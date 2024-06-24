import { createChart, ColorType} from "lightweight-charts";
import React, { useEffect, useRef } from 'react';


const data = [
  {time: '2019-04-11', value: 80.01},
  {time: '2019-04-12', value: 96.63},
  {time: '2019-04-13', value: 76.64},
  {time: '2019-04-14', value: 81.89},
  {time: '2019-04-15', value: 74.43},
  {time: '2019-04-16', value: 80.01},
  {time: '2019-04-17', value: 96.63},
  {time: '2019-04-18', value: 76.64},
  {time: '2019-04-19', value: 81.89},
  {time: '2019-04-20', value: 74.43},
  {time: '2019-04-21', value: 100},
];

export default function App() {

  const chartContainerRef = useRef("chart");

  useEffect(() => {
    const chart = createChart(chartContainerRef.current, {
      layout: {
        background: { type: ColorType.Solid, color: 'white' }
      },
      width: 500,
      height: 200
    });


    const newSeries = chart.addAreaSeries({
      lineColor: "#2962FF",
      topColor: "#2962FF",
      bottomColor: "rgba(41, 98, 255, 0.3)"
    })

    newSeries.setData(data);
  }, [])




  return (
  <div id={chartContainerRef.current}> </div>
  );
}