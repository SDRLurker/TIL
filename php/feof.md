**출처**

[https://stackoverflow.com/questions/32029647/warning-feof-expects-parameter-1-to-be-resource-boolean-given-in-volume1-we](https://stackoverflow.com/questions/32029647/warning-feof-expects-parameter-1-to-be-resource-boolean-given-in-volume1-we)

# 경고 : feof()는 첫 번째 매개 변수가 리소스가 될 것으로 예상합니다. 하지만은 /volume1/web/comment.php 62행에 boolean 타입입니다.

저는 youtube와 다른 사이트의 도움으로 코드를 학습하기 시작했습니다만, 문제에 직면하였습니다. 여기에 제 코드입니다.

```php
<form action="" method="post" id="c">
  <label> Name: <br><input type="text" name="name" size="36"></label><br></br>
  <label> Message: <br><textarea cols="35" rows="5" name="mes"></textarea></label><br></br>
  <input type="submit" name="submit" value="Submit" class="texty" >
</form>

<?php
$post = $_POST["post"];
$name = $_POST["name"];
$text = $_POST["mes"];

if ($post) {

    #WRITE DOWN COMMENTS#

    $write = fopen("c.txt", "a+");
    fwrite($write, "<u><b> $name</b></u><br>$text<br></br>");
    fclose($write);

    #DISPLAY COMMENTS#

    $read = fopen("c.txt", "r+t");
    echo "All comments:<br>";

    while (!feof($read)) {   #this line does the error#
        echo fread($read, 1024);
    }
    fclose($read);
}
else{
    #DISPLAY COMMENTS#

    $read = fopen("c.txt", "r+t");
    echo "All comments:<br>";

    while (!feof($read)) {
        echo fread($read, 1024);
    }
    fclose($read);
}
?>
```

그래서 2개의 파일이 있는데, 하나는 당신이 코멘트를 입력하고 그것을 게시하는 곳이고, 다른 하나는 당신이 입력한 것을 붙여 넣은 다음 페이지에 다시 출력되는 .txt 파일입니다. 파일 권한에 문제가 있는 것 같습니다. "r"에만 입력하면 오류가 발생하지 않지만 게시하려는 내용이 저장되지 않기 때문입니다. 감사합니다. 읽고 답장해 주셔서 감사합니다.

---

## 2 개의 답변 중 1 개의 답변

`fopen`은 파일을 열 수 없는 경우 부울(`FALSE`) 타입을 반환합니다. `feof`에 전달하기 전에 `$read`가 false가 아닌지 확인해야 합니다. 그리고 왜 파일을 읽을 수 없는지 알아 내십시오.

```php
if ($read) {
    ...
```

권한으로 인해 또는 파일 자체를 찾을 수 없는 경우 파일을 읽을 수 없습니다. `c.txt`를 참조하고 있지만 PHP가 있는 동일한 디렉토리에 있습니까? `getcwd`를 사용하여 PHP가 어떤 디렉토리에 있는지 확인할 수 있습니다.

```php
echo getcwd()
```

또한 form 핸들러는 `$_POST['post']`에 값이 있지만 해당 이름이 form에서 사용되지 않는 경우에만 쓰기 분기문에 들어갑니다 (적어도 표시된 예제에서는 사용되지 않았습니다).
