EXECUTE=`basename $0`
SERVER=$1
RUNIT=$2
GROUP="GROUP"	# 넘길서버의 동일한 부분의 도메인이나 IP를 GROUP 자리에 입력

#사용법 함수
usage()
{

local MESSAGE=$1
echo "-----------------------------------------------------------------------"
echo "사용방법 1) $EXECUTE 실행할서버 \"명령\""
echo "-----------------------------------------------------------------------"
echo "넘길서버) 쉼표로 구분하여 그룹의 마지막 자리만 입력"
echo "          $EXECUTE 1,2,3,4"
echo "          => 현재서버에서 ${GROUP}1,...,${GROUP}4로 같은 명령을 실행"
echo "-----------------------------------------------------------------------"
echo $MESSAGE
echo "-----------------------------------------------------------------------"

}

if [ -z "$SERVER" ]; then
	usage "상태) 실행할 서버를 지정하지 않음!!"
    exit 0
fi

if [ -z "$RUNIT" ]; then
	usage "상태) 명령어를 파일지정하지 않음!!"
    exit 0
fi

echo "실행할서버 = [$SERVER]"
echo "명령 = [$RUNIT]"

IFS=','
for x in $SERVER
do
    echo "rsh $GROUP$x $RUNIT"
    rsh $GROUP$x $RUNIT
done
