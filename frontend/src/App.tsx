import React from 'react';
import logo from './logo.svg';
import './App.css';
import { appStore } from './store/AppStore';
import { observer } from 'mobx-react-lite';
import { Button } from 'antd';

function App() {

  const handleClickEvent = () => {
    appStore.addData(1);
  }


  return (
    <div>
      {appStore.data}
      <Button type="primary" onClick={handleClickEvent}>버튼</Button>
    </div>
  );
}

export default observer(App);
