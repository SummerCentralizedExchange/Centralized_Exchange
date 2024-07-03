import { OrderBook } from '@lab49/react-order-book';

export default function StyledOrderBook({ book }: { book: { bids: string[][], asks: string[][] } }) {
  // Ensure bids and asks are defined and are arrays
  const { bids = [], asks = [] } = book;

  // Ensure there are elements in bids and asks
  if (!bids.length || !asks.length) {
    return <div>Order book data is not available</div>;
  }

  return (
    <>
      <style
        dangerouslySetInnerHTML={{
          __html: `
            .MakeItNice {
              font-family: -apple-system, BlinkMacSystemFont, sans-serif;
              font-size: 13px;
              font-variant-numeric: tabular-nums;
              display: inline-block;
              background-color: #070F15;
              color: rgba(255, 255, 255, 0.6);
              padding: 50px 0;
            }

            .MakeItNice__side-header {
              margin: 0 0 5px 0;
              font-weight: 700;
              text-align: right;
            }

            .MakeItNice__list {
              list-style-type: none;
              padding: 0;
              margin: 0;
            }

            .MakeItNice__list-item {
              cursor: pointer;
              padding: 3px 50px 3px 20px;
              display: flex;
            }

            .MakeItNice__list-item:hover {
              background: rgb(18, 29, 39);
            }

            .MakeItNice__price {
              flex: 0 0 70px;
              color: var(--row-color);
              text-align: right;
              display: inline-block;
              margin-right: 15px;
            }

            .MakeItNice__size {
              flex: 0 0 70px;
            }

            .MakeItNice__spread {
              border-width: 1px 0;
              border-style: solid;
              border-color: rgba(255, 255, 255, 0.2);
              padding: 5px 20px;
              text-align: center;
              display: flex;
            }

            .MakeItNice__spread-header {
              margin: 0 15px 0 0;
              flex: 0 0 70px;
              text-align: right;
            }

            .MakeItNice__spread-value {
              width: 28px;
              overflow: hidden;
            }
          `,
        }}
      />

      <OrderBook
        book={{ bids, asks }}
        fullOpacity
        interpolateColor={(color) => color}
        listLength={10}
        stylePrefix="MakeItNice"
      />
    </>
  );
}
