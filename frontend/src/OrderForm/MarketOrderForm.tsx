import React, { useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { Button, Modal } from 'react-bootstrap';
import axios from 'axios';
import UtilsSymbol from '../Utils/UtilsSymbol';

type FormValues = {
  side: 'Buy' | 'Sell';
  quantity: number;
  marketUnit:string;
};

const SERVER_ADDRESS = process.env.REACT_APP_ADDRESS;

export default function MarketOrderForm({symbol, token}:{symbol:UtilsSymbol, token:string}) {
  const { register, handleSubmit, formState: { errors } } = useForm<FormValues>();
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const onSubmit: SubmitHandler<FormValues> = async (data) => {
    var postData = {
      symbol: `${symbol.baseCoin}${symbol.quoteCoin}`,
      type: 'Market',
      price: 0,
      ...data
    };

    const config = {
      headers: { Authorization: `Bearer ${token}` }
  };
    console.log(`sending post request: ${JSON.stringify(postData)} with token ${token}`);
    
    try {
      const response = await axios.post(`${SERVER_ADDRESS}/order/create`, postData, config);
      console.log('Order created:', response.data);

      if (response.status === 200) {
        window.alert('Success! Order created.');
      } else {
        window.alert('Error with creating order.');
      }
    } catch (error) {
      console.error('Error creating order:', error);
    }

    

    handleClose();
  };

  return (
    <>
      <div style={{ display: 'flex', justifyContent: 'center', margin: '5px' }}>
        <Button variant="dark" onClick={handleShow}>
          Place <b>Market</b> order!
        </Button>
      </div>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton style={{ backgroundColor: '#282c34', borderBottom: '1px solid #444' }}>
          <Modal.Title style={{ color: 'white' }}>Place order for {`${symbol.baseCoin}${symbol.quoteCoin}`}</Modal.Title>
        </Modal.Header>
        <Modal.Body style={{ backgroundColor: '#282c34', color: 'white' }}>
          <form onSubmit={handleSubmit(onSubmit)}>
            <div className="form-group">
              <label htmlFor="type">Type:</label>
              <select id="type" className="form-control" {...register("side", { required: true })}>
                <option value="Buy">Buy</option>
                <option value="Sell">Sell</option>
              </select>
              {errors.side && <span className="text-danger">This field is required</span>}
            </div>

            <div className="form-group">
              <label htmlFor="type">Market Unit:</label>
              <select id="type" className="form-control" {...register("marketUnit", { required: true })}>
                <option value="baseCoin">{symbol.baseCoin}</option>
                <option value="quoteCoin">{symbol.quoteCoin}</option>
              </select>
              {errors.side && <span className="text-danger">This field is required</span>}
            </div>

            <div className="form-group">
              <label htmlFor="quantity">Quantity:</label>
              <input
                id="quantity"
                type="number"
                step="any"
                className="form-control"
                {...register("quantity", { required: true, valueAsNumber: true })}
              />
              {errors.quantity && <span className="text-danger">This field is required and must be a number</span>}
            </div>

            <Button type="submit" variant="dark" style={{marginTop:'5px'}}>
              Send
            </Button>
          </form>
        </Modal.Body>
      </Modal>
    </>
  );
}



const styles = `
form {
    max-width: 400px;
    margin: 20px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  
  form div {
    margin-bottom: 15px;
  }
  
  label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
  }
  
  input, select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
  }
  
  input:focus, select:focus {
    border-color: #66afe9;
    outline: none;
    box-shadow: 0 0 5px rgba(102, 175, 233, 0.6);
  }
  
  button {
    width: 100%;
    padding: 10px;
    background-color: #28a745;
    border: none;
    border-radius: 4px;
    color: white;
    font-size: 16px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #218838;
  }
  
`
