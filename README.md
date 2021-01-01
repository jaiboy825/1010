# 1010
## 프로젝트 제목 및 설명
모바일게임(1010!) 이라는 블록게임을 자바로 만들었다. 10x10 사이즈의 판이 있고 블록을 드래그 앤 드롭을 하여 원하는 위치에 두는 게임이다.
- 가로든 세로든 10칸을 다 채운다면 그 줄은 지워진다. 
- 블록이 3개씩 생성되고 3개를 모두 사용하면 다음 블록이 생성되며 그 모양은 랜덤으로 생성 된다.
- 만약 블록이 들어갈 공간이 없다면 게임오버. 
일상생활에서 짧은 시간이지만 핸드폰으로 이 게임을 재밌게 플레이 하였기에 컴퓨터를 사용하는 중에도 심심할 때 한 번씩 플레이를 하면 좋겠다고 생각하여 만들었다.

### 프로젝트에서 데이터베이스의 활용방안
로그인 및 회원가입 기능을 만들었고 로그인 후 게임을 시작하면 로그아웃 전 게임의 진행상황이 자동으로 불러온다.

그리고 아이디별로 BestScore(최고점수)를 확인할 수 있는 label을 만들었다. 

### 프로젝트 개발기간 중 어려웠던 점과 극복방안
게임 판 정보가 배열에 담겨 있어 데이터베이스에 어떤 식으로 게임 판과 다음 블록을  저장해야 할지 전혀 감이 오지 않았다. 
그러다가 배열로 저장하지 않아도 text형으로 저장했다 가져와서 원하는 형태로 바꾸어서 사용하자고 생각했고 성공했다.
bgm 관련해서 스레드나 다른 메서드로 여러 번 했었는데 그 과정에서 어떻게 사용해야 될지 감이 잡히지 않아서 조금 어려웠다.
### 프로젝트를 통해서 느낀 점
- 우리가 배운 기능들은 극히 일부라는 것을 새삼 깨달았고 직접 공부해서 성공시켰을 때 수업시간에 배워서 익힐 때보다 뿌듯하고 재밌었다. 
- 학교 수업에서 배운 것을 활용했지만 그것만으로 부족했다는 것을 알게 되었고, 앞으로 연습을 많이 해야겠다고 생각한다. 
- 둘이든 셋이든 협동해서 만든다면(다 열심히 하는 가정 하에)좋은 결과를 가질 수 있을 것이라고 생각한다. 

## 프로젝트 이미지 및 UI 사용법
#### 시작 화면
![1010](https://user-images.githubusercontent.com/55534787/100031353-ca2e2480-2e38-11eb-885d-5b1a36470c53.png)
- 처음에 게임에 들어오면 회원가입과 로그인이 된다. 비밀번호를 치고 enter 키를 눌러도 로그인이 된다.
- 오른쪽 상단에 버튼으로 게임 배경음악인 Soul & Mind(항아리 게임 배경음악)를 음소거 하거나 다시 킬 수 있다.

#### 게임 화면
![1010-1](https://user-images.githubusercontent.com/55534787/103434075-a0d69480-4c3f-11eb-9a94-35a24c79169b.png)
- 로그인을 해서 들어가면 여러 가지 버튼이 있는데 먼저 왼쪽 상단에 음표 버튼은 로그인 화면과 같은 역할을 하지만 옵션에서 선택한 음량으로 할 수 있다. 
- 오른쪽 new game 으로 게임오버나 게임을 플레이하던 중간에 새로운 게임을 하고 싶을 때 누르면 새 게임이 불러와진다. 
- 그리고 게임을 플레이 하던 중 로그아웃을 하게 되면 하던 게임과 점수가 저장되어 나중에 다시 로그인 하였을 때 하던 게임을 이어 할 수 있다.
- 왼쪽 상단에 있던 옵션 버튼을 누르게 되면 위 사진과 같은 화면이 나오는데 먼저 볼륨 조절 슬라이더로 기본 값은 0.3이며 원하는 음량으로 설정 할 수 있다.
- 블록 색상을 설정할 수 있는데 red, green, blue 이 세 슬라이더를 움직여서 원하는 블록 색상을 설정할 수 있다.

#### 색상 조정 화면
![1010-2](https://user-images.githubusercontent.com/55534787/103434076-a0d69480-4c3f-11eb-8935-082f157dfcfd.png)
![1010-3](https://user-images.githubusercontent.com/55534787/103434077-a16f2b00-4c3f-11eb-8d6f-19b5ab13caf8.png)
ok 버튼을 누르면 블록 색상이 설정 된다.(로그아웃 시 블록 색상은 초기화 된다.)
#### 색상 바꾼 뒤 화면
![1010-4](https://user-images.githubusercontent.com/55534787/103434078-a16f2b00-4c3f-11eb-83ad-fdaeff75ceb3.png)

#### 게임 화면
![1010-5](https://user-images.githubusercontent.com/55534787/103434074-9fa56780-4c3f-11eb-9504-d6a95deb1375.png)
게임 오버 화면 이다. 더 이상 드래그 앤 드롭은 안되며 new game 버튼을 눌러 새 게임을 시작할 수 있다.
