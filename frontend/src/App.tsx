import React from 'react';
import logo from './logo.svg';
import './App.css';
import { appStore } from './store/AppStore';
import { observer } from 'mobx-react-lite';
import { Button } from 'antd';
import { useEffect } from 'react';
import SockJS from 'sockjs-client';

function App() {

  const handleClickEvent = () => {
    appStore.addData(1);
  }

  useEffect(() => {
      const sockJS = new SockJS("http://localhost:8080/socket-chat");
      
  }, []);


  return (
    <div>

      {appStore.data}
      <Button type="primary" onClick={handleClickEvent}>버튼</Button>
    </div>
  );
}

export default observer(App);
