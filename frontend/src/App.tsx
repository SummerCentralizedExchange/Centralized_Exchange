import { createChart, ColorType} from "lightweight-charts";
import React, { useEffect, useRef, useState } from 'react';
import SymbolHeader from "./SymbolHeader/SymbolHeader";
import 'bootstrap/dist/css/bootstrap.min.css';
import StyledOrderBook from "./StyledOrderBook/StyledOrderBook";
import OrderForm from "./OrderForm/OrderForm";
import axios from 'axios';
import LoginModal from "./LoginModal/LoginModal";
import UtilsSymbol from "./Utils/UtilsSymbol";
import MarketOrderForm from "./OrderForm/MarketOrderForm";
import { OrderBook } from "@lab49/react-order-book";

const SERVER_ADDRESS = process.env.REACT_APP_ADDRESS;

export default function App() {

  const chartContainerRef = useRef<HTMLDivElement>(null);
  const [symbol, setSymbol] = useState<UtilsSymbol>({ baseCoin: "BTC", quoteCoin: 'USDT' }); { /* TODO: now we use default `Test`, but should make request to server with getting some symbol */}
  const [symbolList, setSymbolList] = useState<string[]>([]);

  const [orderBookData, setOrderBookData] = useState<{ bids: string[][], asks: string[][] }>({asks: [],bids: []});
  const orderBookNotEmpty = () => {return orderBookData.asks.length !== 0 || orderBookData.bids.length !== 0} 

  const [showModal, setShowModal] = useState(true);
  const handleClose = () => setShowModal(false);
  const [userName, setUserName] = useState<string | null>(null);
  const [token, setUserToken] = useState<string>("");
  

  useEffect(() => {
    // Get symbols
    const fetchSymbolList = async () => {
      try {
        const response = await axios.get(`${SERVER_ADDRESS}/symbols`);
        console.log("Fetching symbols");
        console.log(response.data);
        setSymbolList(["Test", ...symbolList]); /* TODO: Remove `Test` from symbol list */
        console.log(response.data);
      } catch (error) {
        console.error('Error fetching symbol list:', error);
      }
      
      fetchSymbolList();
    };


  }, []);

  //
  useEffect(() => {
    // Check if the user is already authenticated
    const token = localStorage.getItem('Authorization');
    console.log(`User name: ${userName}`)

    if(userName === null) {
      setShowModal(true);
    }

    if (token && userName !== null) {
      setShowModal(false);
    }
  }, []);

  //
  useEffect(() => {
    const fetchOrderBook = async () => {
      try {
        const response = await axios.get(`${SERVER_ADDRESS}/market/orderbook?symbol=${mergedSymbol}`);
        console.log(response.data);
        setOrderBookData({bids:response.data.bids, asks:response.data.asks});
        console.log(orderBookData);
      } catch (error) {
        console.error('Error fetching order book:', error);
      }
    };

    // First call to monting component.
    fetchOrderBook();

    // Interval to requesting data.
    const intervalId = setInterval(fetchOrderBook, 1000); // 5000 ms = 5 second.

    // Clear interval.
    return () => clearInterval(intervalId);
  }, []);
  //


  useEffect(() => {
    console.log("Render");

    if (chartContainerRef.current === null) {
      throw Error();
    }

    const chart = createChart(chartContainerRef.current, {
      layout: {
        background: { type: ColorType.Solid, color: '#151825' }
      },
      width: chartContainerRef.current.clientWidth,
      height: 500
    });
    const candlestickSeries = chart.addCandlestickSeries({
      upColor: '#26a69a', downColor: '#ef5350', borderVisible: false,
      wickUpColor: '#26a69a', wickDownColor: '#ef5350',
    });

    candlestickSeries.setData([
        { time: '2024-07-01', open: 75.16, high: 82.84, low: 36.16, close: 45.72 },
        { time: '2024-07-02', open: 45.12, high: 53.90, low: 45.12, close: 48.09 },
        { time: '2024-07-03', open: 60.71, high: 60.71, low: 53.39, close: 59.29 },
        { time: '2024-07-04', open: 68.26, high: 68.26, low: 59.04, close: 60.50 },
        { time: '2024-07-05', open: 67.71, high: 105.85, low: 66.67, close: 91.04 },
        { time: '2024-07-06', open: 91.04, high: 121.40, low: 82.70, close: 111.40 },
        { time: '2024-07-07', open: 111.51, high: 142.83, low: 103.34, close: 131.25 },
        { time: '2024-07-08', open: 131.33, high: 151.17, low: 77.68, close: 96.43 },
        { time: '2024-07-09', open: 106.33, high: 110.20, low: 90.39, close: 98.10 },
        { time: '2024-07-10', open: 109.87, high: 114.69, low: 85.66, close: 111.26 },
    ]);

    chart.timeScale().fitContent();

    return () => {chart.remove();};
  }, []);


  const mergedSymbol = `${symbol.baseCoin}${symbol.quoteCoin}`;

  return (
    <div style={{backgroundColor:'#282c34'}}>
      <LoginModal show={showModal} handleClose={handleClose} setUserName={setUserName} setUserToken={setUserToken} />
      <SymbolHeader symbol={mergedSymbol} userName={userName} symbolList={symbolList} />
      <StyledOrderBook book={orderBookData}/>
      <OrderForm symbol={mergedSymbol} token={token}/>
      {orderBookNotEmpty() && <MarketOrderForm symbol={symbol} token={token}/>}
      <div ref={chartContainerRef}> </div>
    </div>
  );
}