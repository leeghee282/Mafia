# 

# 로그인, 회원가입 요청 보낼 때 필요한 부분은 값을 채워주고, 필요없는 부분은 null을 써주도록 하자 (테이블에 정의된 요소를 모두 써주어야 한다는 말. 순서까지 똑같이 지킬 필요는 없음. no는 쓸 필요 없음)

![](공통_요청_정리_assets/2022-07-29-10-51-28-image.png)

ex) login의 경우

{

    "email": null,

    "id": "입력",

    "imagePath": null,

    . . . 

    이런 식으로 필요한 부분은 값 입력, 필요없는 부분은 null

}

# 회원정보 수정 요청의 경우 백 코드는 다음과 같다

![](공통_요청_정리_assets/2022-07-29-12-03-01-image.png)

user.blabla로 접근해야 하는 값이 정해져 있기 때문에, 저기 명시된 id, nickname, intro, password, imagePath 값을 넣어주어야 한다. 수정할 부분은 별도의 수정할 값으로, 그 외의 값은 store에 저장된(로그인시 store에 모든 정보를 넣어둔다) 값을 이용하여 채워주자. 

# 로그인

- /user/login
  
  - method:
    
    - POST
  
  - body:
    
    ```json
    {
        "id": "아이디",
        "password": "비번"
    ```

# 

# 회원가입

- /user/
  
  - method:
    
    - POST
  
  - body:
    
    - ```json
      {    
          "userName": "이"
          "id": "아이디",
          "password": "비번",
          "nickname": "",
          "email": "",
          "myPoint": "",
          "imagePath": "",
      }
      ```

# 마이페이지

- /user/{id}
  
  - method:
    - GET
  - body:
    - key: Authorization
    - value: 로그인, 회원가입시 받은 토큰값

- 리턴형식
  
  - ```json
    {    
    
    }
    ```

# 

# 회원정보 수정

- /user/{id}
  
  - method:
    - PUT
  - header:
    - key: Authorization
    - value: 로그인, 회원가입시 받은 토큰값

- 리턴형식
  
  - ```json
    {    
    
    }
    ```

# 

# 팔로우 요청

- /follow
  
  - method:
    
    - POST
  
  - body
    
    - 팔로우 하는사람, 팔로우 받는 사람 id

# 친구 목록 불러오기

- /follow
  
  - method:
    
    - GET
  
  - body
    
    - 유저 id

# 

# 친구 삭제

- /follow
  
  - method:
    
    - DELETE
  
  - body
    
    - 삭제 요청하는 사람, 삭제 당하는 사람 id

# 

# 유저 검색

- /user/list
  
  - method:
    
    - GET
  
  - body
    
    - 검색어
    
    - 페이지
    
    - 오프셋

# 

# 로비

- /room
  
  - method:
    - GET
  - body

- 리턴형식
  
  - ```json
    {    
    
    }
    ```

# 

# 방생성

- /room
  
  - method:
    - POST

# 방 입실

입실 로직 :  

1. front 에서 입장하기 누름  

2. /room/{roomNo} 로 id 를 body에 담아 백으로 보냄  

3. 입장 불가능시 불가능 메시지 받음 가능 시 다음으로 진행  

4. 웹소켓 접속 후 /receiveProfile 로 입장 내용을 보냄
- /room/{roomNo}
  
  - method:
    
    - PUT
  
  - body
    
    - 방참여 가능 불가능 판단하여 리턴해줌

- ```json
  {    
      f->b
      id : string,
      roomNo : int,
      password : string//비밀번호 없으면 ""
  }    
  ```
  
  b->f :
  ok  사인 및 애러코드 (리턴값, success, fail로 구분)

# 방 삭제

- /room/{roomNum}
  
  - method:
    
    - DELETE

- 리턴형식
  
  - ```json
    {    
    
    }
    ```

# 웹소켓

(stomp 사용)

# 1.대기화면

# 1-1 채팅창

소켓 연결시 사용할 uri

"https://i7e107.p.ssafy.io/roomSocket"

ㅇ

Endpoint(roomSocket)

//프론트 입장에서 데이터를 담아 보내줄 uri
("/receiveChat") 

//프론트 입장에서 구독하다가 데이터를 받을  uri
//send로 메시지를 반환합니다.
("topic/sendChat/{roomNo}")

ex)topic/sendChat/107

- 리턴형식
  
  - ```json
    { 
    ("/receiveChat")
        f->b   
      roomNo: int,
      nickname:String,
      contant:String
    }
    { 
    ("topic/sendChat/{roomNo}")
        b->f   
      nickname:String,
      contant:String
    }
    ```

# 1.대기화면

1. front 에서 입장하기 누름  

2. /room/{roomNo} 로 id 를 body에 담아 백으로 보냄  

3. 입장 불가능시 불가능 메시지 받음 가능 시 다음으로 진행  

4. 웹소켓 접속 후 /receiveProfile 로 입장 내용을 보냄
   
   방 나오는거 까지 소켓으로 통신

# 1-2프로필화면(입퇴실체크)

//프론트 입장에서 데이터를 담아 보내줄 uri
("/receiveProfile")

//프론트 입장에서 구독하다가 데이터를 받을 uri
//send로 메시지를 반환합니다.
("topic/sendProfile/{roomNo}")

- 리턴형식
  
  - ```json
    { 
    ("/receiveProfile")
        f->b
        sessionId:string,
      roomNo: int,
      id:String
    }
    
    { 
    ("topic/sendProfile/{roomNo}")
        b->f  
    progress:String,//in and out 으로 입장인지 퇴장인지 구별하 
      userList{
        nickname:String,
        id:String,//out일땐 id값만 줄 예
        imagePath:String,
        intro:String,
        ifCaptain: bool,
        ifReady: bool,//레디 상태 저장용 지금 이정보를 백에서 알 수 없어서 db에 추
        winRate:int//퍼센트로 
      }
    }
    ```

# 

# 

# 1-3 ready 버튼

//프론트 입장에서 데이터를 담아 보내줄 uri
("/receiveReady")

//프론트 입장에서 구독하다가 데이터를 받을 uri
//send로 메시지를 반환합니다.
("topic/sendReady/{roomNo}")

- 리턴형식
  
  - ```json
    { 
    ("/receiveReady")
        f->b    
      roomNo: int,
    
      //방장만 true로 보낼 수 있어야함. true : 게임 시작 하겠다.  f :  대기
      StartGame: bool,
      ifReady: bool,//true : ready 상태  f :  레디안함
      id:String
    }
    
    { 
    ("topic/sendReady/{roomNo}")
        b->f
      StartGame: bool,//true : 인게임으로 넘어가.  f :  대기
        ifStart: bool,//true : start 가능상태  f :  레디안한사람잇
       ifReady: bool,//true : ready 상태  f :  레디안함
       id:String
    }
    ```

# 1-4  방 폭파!

//프론트 입장에서 데이터를 담아 보내줄 uri
("/receiveBreak")

//프론트 입장에서 구독하다가 데이터를 받을 uri
//send로 메시지를 반환합니다.
("topic/sendBreak/{roomNo}")

- 리턴형식
  
  - ```json
    { 
    ("/receiveBreak")
        f->b    //방장만 이 신호를 줄 수 있도록 만들어 놔야
    
      roomNo: int,
    }
    
    { 
    ("topic/sendBreak/{roomNo}")
        b->f  
      roomNo: int//이 신호를 받으면 나가
    }
    ```

# 

# 2. 인게임

**5인 마1 의1 중1 시2**

# 게임 시작

**대기방 소켓 하나에서 계속 ㄱ**

소켓 연결시 사용할 uri

//프론트 입장에서 데이터를 담아 보내줄 uri
("/receiveMafia")

//프론트에서 투표로직 처리 후 값 보내줄 때 사용할 uri

//프론트 입장에서 구독하다가 데이터를 받을 uri
//send로 메시지를 반환합니다.
("/sendMafia/{roomNo}/{id}")

progress : start

백에서 전체에게 보내는 uri

("/sendMafia/{roomNo}")

누가 나갔을 때 백에서 프론트에 보낼 uri

("/sendMafia/{roomNo}/gameOut")

ex)/sendChat/107

- - ```json
    투표 결과를 누가 누구를 뽑았는지 컬러로 표시 해주
    실제 마피아 게임 생각하면 누가 누구 뽑앗는지 표기 해주는게 맞을거같음
    {
      f->b
      progress : start
      roomNo: int,
      id : string, //당장 필요없지만 일단 받으면 편할거같아서 받아둠
      sessionId:string //대기방에서 넘어 올때 소켓이 끊어지면 보내주고 유지된다면 안보내줘야함
    
    }
    
    {
      b->f
      progress : start,
      absoluteTime: hh:mm:ss, (24 기준)
      job : string, // 시민 -> citizen, 의사 -> doctor, 마피아 -> mafia, 미션자 -> mission
      isHost:  bool
      joinUsers: [
        {
        id: string,
        nickname: string,
        color: string
        },
        ]
    }
    ```
    
    ```json
    # 투표화면으로 넘어가기 버튼을 낮에 눌렀을 때 주고받을 정보
    {
        f->b
        progress : day,
    
        roomNo: int,
        id: string,
    }
    
    {
        b->f
        // 누가 넘기기 버튼 누를 때마다 모두에게 전송
        progress : day,
        ifSkip: bool,
        id: string, // 긴급투표 버튼 누른 사람의 id를 넘겨줌
        color: string,
        absoluteTime: hh:mm:ss, (24 기준)
    }
    
    // 과반수 이상의 투표자가 나오면 스킵 가능
    ```
    
    ```json
    // 투표했을 때(투표확정 버튼을 눌렀을 때)
    {
     f->b
        progress : voteDay,
        roomNo: int,
        id : string,
        nickname : string,
        job : string,
        vote : string,//누구를 뽑았는지 id
        //미션 관련으로 승리 했을 경우 알려주는 변수
        ifWin : bool
    }
    
    {
    b->f
    progress : voteDay,
    id : string,
    votedId: string,
    }
    
    {
        b -> f
        progress: voteDayFinish,
        id: string, // 투표 결과 없을 때 -> ""
        winJob: string, // 승리자 없을 때 -> "", 시민 승 -> citizen, 마피아 승 -> mafia, 미션자 승 -> mission
        nickname: string, // 뽑힌사람의 닉네임
    }
    ```
  
  {
  
    {

```json
프론트에서 투표 끝내고 백으로 정보 보내주는 부분
// 밤 투표

  f->b
마피아가 여러명이면
소켓으로 구현 가능함
{
    progress : voteNight,
    roomNo: int,
    id : string,
    nickname : string,
    job : string,
    vote : string,//누구를 뽑았는지 id
    //미션 관련으로 승리 했을 경우 알려주는 변수
    ifWin : bool
}
```

    }

```json
{
b->f
progress : voteNight,
winJob: string, // 승리자 없을 때 -> "", 시민 승 -> citizen, 마피아 승 -> mafia, 미션자 승 -> mission
votedId : string, // 누가 뽑혔는지(누가 죽었는지)
nickname : string // 죽은 사람의 닉네임
}
```

```json
// 어떤 유저가 나갔을 때(소켓 끊겼을 때)
// 얘 전용 uri -> (/gameOut)
{
    b->f
    id: string, // 나간사람 id
    winJob: string, // 나감으로 인해 판별된 승리팀, 승리자 없을 때 -> "", 시민 승 -> citizen, 마피아 승 -> mafia, 미션자 승 -> mission
}
```
