import React from 'react';
import logo from './logo.svg';
import './App.css';
import { appStore } from './store/AppStore';
import { observer } from 'mobx-react-lite';
import { Button, Row, Table, Typography, Input, TableColumnProps, Space } from 'antd';
import { useEffect } from 'react';
import SockJS from 'sockjs-client';
import styled from 'styled-components';
import { ColumnsType } from 'antd/lib/table';

const FlexBody = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid black;
  margin: 50px;
  padding: 10px;
`;

const chatColumn: ColumnsType<any> = [{
  title: "채팅방 id",
  dataIndex: "num",
  key: "num"
},
{
  title: "제목",
  dataIndex: "title",
  key: "title"
},
{
  title: "행동",
  dataIndex: "action",
  render: () => (
    <Button type={"primary"}>
      입장
    </Button>
  )
},
]

const fakeData = [{
  num: 0,
  title: "김동근님의 채팅방"
}, {
  num: 1,
  title: "김동근님의 채팅방"
},
{
  num: 2,
  title: "김동근님의 채팅방"
}, {
  num: 3,
  title: "김동근님의 채팅방"
}
]

function App() {

  useEffect(() => {
    const sockJS = new SockJS("http://localhost:8080/socket-chat");

  }, []);


  return (
    <FlexBody>
      <Row style={{ width: "100%", display: "flex" }}>
        <span style={{ flex: 7 }}></span>
        <Typography.Title level={4}>채팅방 목록</Typography.Title>
        <span style={{ flex: 5 }}></span>
        <Button>방만들기</Button>
      </Row>


      <Row style={{ width: "100%", display: "flex" }}>
        <span>닉네임 :</span> <Input style={{ marginLeft: "5px", flex: "1" }} />
      </Row>

      <Row style={{ width: "100%" }}>
        <Table style={{ width: "100%" }}
          columns={chatColumn}
          dataSource={fakeData} />
      </Row>

    </FlexBody>
  );
}

export default observer(App);
