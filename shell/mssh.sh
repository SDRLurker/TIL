EXECUTE=`basename $0`
SERVER=$1
SPORT=$2
RUNIT=$3
GROUP="GROUP"        # 넘길서버의 동일한 부분의 도메인이나 IP를 GROUP 자리에 입>력합니다.
PASSWD="PASSWORD"    # 넘길서버의 비밀번호를 입력합니다.
                     # GROUP에 해당하는 서버 모두 같은 비번이라 가정합니다.

#사용법 함수
usage()
{

local MESSAGE=$1
echo "-----------------------------------------------------------------------"
echo "사용방법 1) $EXECUTE 실행할서버 SSH포트 \"명령\""
echo "-----------------------------------------------------------------------"
echo "넘길서버) 쉼표로 구분하여 그룹의 마지막 자리만 입력"
echo "          $EXECUTE 1,2,3,4"
echo "          => 현재서버에서 ${GROUP}1,...,${GROUP}4로 같은 명령을 실행"
echo "-----------------------------------------------------------------------"
echo $MESSAGE
echo "-----------------------------------------------------------------------"

}

# 원격 서버에 접속하여 shell 명령을 수행합니다.
sexecute()
{

local IP=$1
local PORT=$2
local PW=$3
local MSG=$4 

if [ -z $PORT ]; then
	PORT=22
fi

echo "ssh -p$PORT $IP $MSG"
expect <<EOF
set timeout -1
spawn ssh -p$PORT $IP $MSG
expect "password:"
send "$PW\r"
expect eof
EOF

}

if [ -z "$SERVER" ]; then
    usage "상태) 실행할 서버를 지정하지 않음!!"
    exit 0
fi

if [ -z "$SPORT" ]; then
    usage "상태) SSH 포트번호를 지정하지 않음!!"
    exit 0
fi

if [ -z "$RUNIT" ]; then
    usage "상태) 명령어를 지정하지 않음!!"
    exit 0
fi

echo "실행서버 = [$SERVER]"
echo "포트 = [$SPORT]"
echo "명령 = [$RUNIT]"

IFS=','
for x in $SERVER
do
    sexecute $GROUP$x $SPORT $PASSWD $RUNIT
done
