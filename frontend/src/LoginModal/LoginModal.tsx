import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { useForm, SubmitHandler } from 'react-hook-form';
import axios from 'axios';

const SERVER_ADDRESS = process.env.REACT_APP_ADDRESS;

interface LoginModalProps {
  show: boolean;
  handleClose: () => void;
  setUserName: (name: string) => void;
}

interface FormValues {
  username: string;
  password: string;
}

const LoginModal: React.FC<LoginModalProps> = ({ show, handleClose, setUserName }) => {
  const { register, handleSubmit, formState: { errors } } = useForm<FormValues>();
  const [isSignup, setIsSignup] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const onSubmit: SubmitHandler<FormValues> = async (data) => {
    try {
      const endpoint = isSignup ? '/auth/signup' : '/auth/signin';
      const response = await axios.post(`${SERVER_ADDRESS}${endpoint}`, data);
      
      if (response.status === 200 && response.data) {
        localStorage.setItem('Authorization', `Bearer ${response.data}`);
        console.log('Bearer token: ',response.data)
        setUserName(data.username);
        window.alert('Successfully logged in!');
        setIsLoggedIn(true);
        handleClose();
      }
    } catch (error) {
      console.error('Error during authentication:', error);
      window.alert('Authentication failed.');
    }
  };

  if (!show) return null;

  return (
    <Modal show={!isLoggedIn} onHide={isLoggedIn ? undefined : handleClose} backdrop="static">
      <Modal.Header closeButton>
        <Modal.Title>{isSignup ? 'Sign Up' : 'Sign In'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <Form.Group controlId="username">
            <Form.Label>Username</Form.Label>
            <Form.Control 
              type="text" 
              placeholder="Enter username" 
              {...register('username', { required: true })}
            />
            {errors.username && <span>This field is required</span>}
          </Form.Group>

          <Form.Group controlId="password">
            <Form.Label>Password</Form.Label>
            <Form.Control 
              type="password" 
              placeholder="Password" 
              {...register('password', { required: true })}
            />
            {errors.password && <span>This field is required</span>}
          </Form.Group>

          <Button variant="primary" type="submit">
            {isSignup ? 'Sign Up' : 'Sign In'}
          </Button>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={() => setIsSignup(!isSignup)}>
          {isSignup ? 'Switch to Sign In' : 'Switch to Sign Up'}
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default LoginModal;
