const { spawn } = require('child_process');
// ssh ??? 접속
const child = spawn('ssh', ['???']);

child.on('exit', function (code, signal) {
  console.log('child process exited with ' +
              `code ${code} and signal ${signal}`);
});

child.stdout.on('data', (data) => {
  console.log(`child stdout:\n${data}`);
  // 화면에서 login ok가 출력되면
  if( data.indexOf("login ok") > -1) {
    // 3초마다 http, https 접속 확인 shell 명령 전송.
    // \n이 키보드에 엔터키 기능이라 필수적으로 들어가야 함!
    setInterval( () => {
      child.stdin.write("pwd\n");
    }, 3000)
  }
});

child.stderr.on('data', (data) => {
  //console.error(`child stderr:\n${data}`);
});
