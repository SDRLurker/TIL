YY=`date +%y`
MM=`date +%m`
DD=`date +%d`

EXECUTE=`basename $0`
SERVER=$1
CPFILES=$2
CPPATH=$3
GROUP="GROUP"        # 넘길서버의 동일한 부분의 도메인이나 IP를 GROUP 자리에 입력합니다.
PASSWD="PASSWORD"    # 넘길서버의 비밀번호를 입력합니다. 
                     # GROUP에 해당하는 서버 모두 같은 비번이라 가정합니다.
GROUPPORT="22"       # 넘길서버의 ssh포트를 입력합니다. 
                     # GROUP에 해당하는 ssh포트라고 가정합니다.

#사용법 함수
usage()
{

local MESSAGE=$1
echo "-----------------------------------------------------------------------"
echo "사용방법 1) $EXECUTE 넘길서버 넘길파일 [넘길위치]"
echo "-----------------------------------------------------------------------"
echo "사용방법 2) $EXECUTE 넘길서버 넘길파일"
echo "            [넘길위치]를 지정하지 않으면, pwd와 동일 위치로 복사"
echo "-----------------------------------------------------------------------"
echo "넘길서버) 쉼표로 구분하여 그룹의 마지막 자리만 입력"
echo "          $EXECUTE 1,2,3,4"
echo "          => 현재서버에서 ${GROUP}1,...,${GROUP}4로 복사를 수행합니다."
echo "-----------------------------------------------------------------------"
echo $MESSAGE
echo "-----------------------------------------------------------------------"

}

#원격 서버에서 비밀번호를 물어보면 답변합니다.
sexpect()
{

local PW=$1
local MSG=$2

echo $MSG
expect <<EOF
set timeout -1
spawn $MSG
expect "password:"
send "$PW\r"
expect eof
EOF

}

#원격 서버에 접속하여 파일을 복사합니다. 
#해당 파일이 있으면 원격 서버에 백업파일을 만들고 복사합니다.
scheck()
{

local IP=$1
local PW=$2
local PORT=$3
local CPFILE=$4

if [ -z $PORT ]; then
	PORT=22
fi

RES=$(
echo "ssh -P$PORT $IP \"ls $CPPATH/$CPFILE | wc -l\""
expect <<EOF
set timeout -1
spawn ssh -p$PORT -oStrictHostKeyChecking=no $IP "ls $CPPATH/$CPFILE | wc -l"
expect "password:"
send "$PW\r"
expect eof
EOF
)

RESULT=${RES:${#RES}-2:1}

if [ "${RESULT}" == "1" ]; then 
MSG='ssh -p'$PORT' -oStrictHostKeyChecking=no '$IP' "cp -p '$CPPATH'/'$CPFILE' '$CPPATH'/'$CPFILE'.'$YY$MM$DD'"'
sexpect $PW $MSG
fi
MSG='scp -p -P'$PORT' -oStrictHostKeyChecking=no '$CPPATH'/'$CPFILE' '$IP':'$CPPATH'/'$CPFILE
sexpect $PW $MSG

}

if [ -z "$SERVER" ]; then
	usage "상태) [넘길서버]를 지정하지 않음!!"
    exit 0
fi

if [ -z "$CPFILES" ]; then
	usage "상태) [넘길파일]을 파일지정하지 않음!!"
    exit 0
fi

if [ -z "$CPPATH" ]; then
	echo "상태) PATH 지정하지 않아. 현 위치와 동일한 위치로 복사!!"
    CPPATH=`pwd`
fi

echo "넘길서버 = [$SERVER]"
echo "넘길파일 = [$CPFILES]"
echo "넘길위치 = [$CPPATH]"

IFS=','
for CPFILE in $CPFILES
do
    echo "$CPPATH/$CPFILE"
    if [ -f "$CPPATH/$CPFILE" ]; then
        IFS=','
        for x in $SERVER
        do
		    scheck $GROUP$x $PASSWD $GROUPPORT $CPFILE
        done
    fi
done
