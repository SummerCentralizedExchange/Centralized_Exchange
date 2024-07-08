import { OrderBook } from '@lab49/react-order-book';

export default function StyledOrderBook({ book }: { book: { bids: string[][], asks: string[][] } }) {
  // Ensure there are elements in bids and asks
  if (!book.bids.length || !book.asks.length) {
    return <div>Order book data is not available</div>;
  }

  return (
    <>
      <style>
        {styles}
      </style>

      <OrderBook
        book={book}
        fullOpacity
        interpolateColor={(color) => color}
        listLength={10}
        stylePrefix="MakeItNiceAgain"
      />
    </>
  );
}


const styles = `
            .MakeItNiceAgain {
              background-color: #151825;
              color: rgba(255, 255, 255, 0.6);
              display: inline-block;
              font-family: -apple-system, BlinkMacSystemFont, sans-serif;
              font-size: 13px;
              font-variant-numeric: tabular-nums;
              padding: 50px 0;
            }

            .MakeItNiceAgain__side-header {
              font-weight: 700;
              margin: 0 0 5px 0;
              text-align: right;
            }

            .MakeItNiceAgain__list {
              list-style-type: none;
              margin: 0;
              padding: 0;
            }

            .MakeItNiceAgain__list-item {
              border-top: 1px solid rgba(255, 255, 255, 0.1);
              cursor: pointer;
              display: flex;
              justify-content: flex-end;
            }

            .MakeItNiceAgain__list-item:before {
              content: '';
              flex: 1 1;
              padding: 3px 5px;
            }

            .MakeItNiceAgain__side--bids .MakeItNiceAgain__list-item {
              flex-direction: row-reverse;
            }

            .MakeItNiceAgain__side--bids .MakeItNiceAgain__list-item:last-child {
              border-bottom: 1px solid rgba(255, 255, 255, 0.1);
            }

            .MakeItNiceAgain__side--bids .MakeItNiceAgain__size {
              text-align: right;
            }

            .MakeItNiceAgain__list-item:hover {
              background: #262935;
            }

            .MakeItNiceAgain__price {
              border-left: 1px solid rgba(255, 255, 255, 0.1);
              border-right: 1px solid rgba(255, 255, 255, 0.1);
              color: #b7bdc1;
              display: inline-block;
              flex: 0 0 50px;
              margin: 0;
              padding: 3px 5px;
              text-align: center;
            }

            .MakeItNiceAgain__size {
              flex: 1 1;
              margin: 0;
              padding: 3px 5px;
              position: relative;
            }

            .MakeItNiceAgain__size:before {
              background-color: var(--row-color);
              content: '';
              height: 100%;
              left: 0;
              opacity: 0.08;
              position: absolute;
              top: 0;
              width: 100%;
            }

            .MakeItNiceAgain__spread {
              border-width: 1px 0;
              border-style: solid;
              border-color: rgba(255, 255, 255, 0.2);
              padding: 5px 20px;
              text-align: center;
              display: flex;
              justify-content: center;
            }

            .MakeItNiceAgain__spread-header {
              margin: 0 15px 0 0;
              flex: 0 0 70px;
              text-align: right;
            }

            .MakeItNiceAgain__spread-value {
              width: 28px;
              overflow: hidden;
            }
          `