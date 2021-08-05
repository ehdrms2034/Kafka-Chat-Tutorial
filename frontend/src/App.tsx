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
import { Stomp } from "@stomp/stompjs";
import Modal from 'antd/lib/modal/Modal';

const FlexBody = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid black;
  margin: 50px;
  padding: 10px;
`;


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
      stompClient.subscribe("/topic/chat/0", (data) => {
        const message: ChatMessage = JSON.parse(data.body.toString())
        console.log("ㅇㄴㅁㄹ", message);

      });
      stompClient.publish({
        destination: "/app/socket/chat/send",
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
      <Button
        onClick={() => onClickEnterChatRoom(0)}
        type={"primary"}>
        입장
      </Button>
    )
  },
  ]

  const onClickEnterChatRoom = (roomId: number) => {
    appStore.setChatModalOpened(true);
  }

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


      <Modal
        visible={appStore.isChatModalOpened}
        onOk={() => appStore.setChatModalOpened(false)}
        onCancel={() => appStore.setChatModalOpened(false)}
        title={"김동근님의 채팅방"}
        footer={null}
      >

        <div style={{ display: 'flex', width: "100%", height: "50vh", flexDirection: "column" }}>
          <div style={{ flex: 1, overflow: 'scroll' }}>
            <div>동근 : 하이</div>
            <div>당근 : 하이</div>
            <div>둥근 : 하이</div>
            <div>융근 : 하이</div>
            <div>몽근 : 하이</div>
            <div>둥골 : 하이</div>
            <div>하이</div>
            <div>하이</div>
            <div>하이</div>
            <div>하이</div>
            <div>하이</div>
            <div>하이</div>
            <div>하이</div>
            <div>하이</div>
            <div>하이</div>
            <div>하이</div>
          </div>

          <div style={{ display: 'flex', flexDirection: "row" }}>
            <Input></Input>
            <Button>전송</Button>
          </div>


        </div>
      </Modal>
    </FlexBody>
  );
}

export default observer(App);
