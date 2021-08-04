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
import { ChatMessage } from './model/ChatModel';
import { Client, Stomp } from "@stomp/stompjs";

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
    const stompClient = Stomp.over(sockJS);
    const testData: ChatMessage = {
      messageId: 0,
      roomId: 0,
      userId: 0,
      username: "test",
      content: "HelloWorld"
    }
    stompClient.onConnect = () => {
      console.log("소켓 연결됨");
      stompClient.subscribe("/topic/0", (data) => {
        JSON.parse(data.body.toString())
        console.log("ㅇㄴㅁㄹ",data.body);

      });
      stompClient.publish({
        destination:"/app/send",
        body: JSON.stringify(testData)
      });

    }
    stompClient.onDisconnect = () => {
      console.log("소켓 연결끊김");
    }
    stompClient.onStompError = () => {
      console.error("dsfad");
    }

    stompClient.activate();



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
