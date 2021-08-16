import React, { useState } from 'react';
import logo from './logo.svg';
import './App.css';
import { appStore } from './store/AppStore';
import { observer } from 'mobx-react-lite';
import { Button, Row, Table, Typography, Input, TableColumnProps, Space } from 'antd';
import { useEffect } from 'react';
import styled from 'styled-components';
import { ColumnsType } from 'antd/lib/table';
import { ChatMessage, ChatRoom } from './model/ChatModel';
import Modal from 'antd/lib/modal/Modal';

const FlexBody = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid black;
  margin: 50px;
  padding: 10px;
`;


function App() {

  const [chatContent, setChatContent] = useState("");

  useEffect(() => {
    async function init() {
      appStore.updateRooms();
    }
    init();
  }, []);

  const chatColumn: ColumnsType<ChatRoom> = [{
    title: "채팅방 id",
    dataIndex: "roomId",
    key: "roomId"
  },
  {
    title: "제목",
    dataIndex: "title",
    key: "title"
  },
  {
    title: "행동",
    dataIndex: "action",
    render: (dataIndex, data) => (
      <Button
        onClick={() => {
          onClickEnterChatRoom(data.roomId)
        }}
        type={"primary"}>
        입장
      </Button>
    )
  },
  ]

  const onClickEnterChatRoom = (roomId: number) => {
    appStore.subscribeRoom(roomId, (message: ChatMessage) => {
      appStore.receiveMessage(roomId, message);
    });

    const room = appStore.rooms.find(data => data.roomId === roomId);
    appStore.setCurrentRoom(room);
    appStore.setChatModalOpened(true);
  }

  const onHandleAtModalExit = () => {
    appStore.initChatMessages();
    appStore.describeRoom();
    appStore.setChatModalOpened(false);
  }

  const onEnterInChatMessageBar = (roomId: number, content: string) => {
    appStore.sendMessageToRoom(roomId, content);
    setChatContent("");
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
        <span>닉네임 :</span>
        <Input
          value={appStore.username}
          onChange={e => {
            appStore.setUsername(e.currentTarget.value);
          }}
          style={{ marginLeft: "5px", flex: "1" }} />
      </Row>

      <Row style={{ width: "100%" }}>
        <Table style={{ width: "100%" }}
          columns={chatColumn}
          dataSource={appStore.rooms} />
      </Row>


      <Modal
        visible={appStore.isChatModalOpened}
        onOk={() => appStore.setChatModalOpened(false)}
        onCancel={() => { onHandleAtModalExit() }}
        title={"김동근님의 채팅방"}
        footer={null}
      >

        <div style={{ display: 'flex', width: "100%", height: "50vh", flexDirection: "column" }}>
          <div style={{ flex: 1, overflow: 'scroll' }}>
            {appStore.messages.map(chatMessage => (<div>
              {chatMessage.username} : {chatMessage.content}
            </div>)
            )}
          </div>

          <div style={{ display: 'flex', flexDirection: "row" }}>
            <Input
              value={chatContent}
              onChange={e => setChatContent(e.target.value)}
              onPressEnter={e => {
                onEnterInChatMessageBar(1, chatContent);
              }}
            ></Input>
            <Button>전송</Button>
          </div>

        </div>
      </Modal>
    </FlexBody>
  );
}

export default observer(App);
