출처: [https://en.wikipedia.org/wiki/Multiton\_pattern](https://en.wikipedia.org/wiki/Multiton_pattern)

# Multiton 패턴

소프트웨어 엔지니어링에서 **multiton 패턴**은 singleton 패턴을 일반화한 디자인 패턴입니다. singleton은 생성될 클래스의 인스턴스를 하나만 허용하지만, multiton 패턴은 map의 사용을 통해 관리되는 여러개의 인스턴스 생성을 허용합니다.

응용프로그램(예시 java.lang.Runtime) 당 하나의 인스턴스를 가지는 것에 비해 multiton 패턴은 대신에 _key 당_ 하나의 인스턴스를 보장합니다.

대부분의 사람과 교과서는 singleton 패턴을 고려합니다. 예를 들어 multiton은 객체지향 프로그래밍 교과서인 디자인 패턴에 자주 나타나지는 않습니다. (이는 더 유연한 접근인 **singleton들의 레지스트리**라는 이름으로 나타납니다.)

## 설명

multiton이 액세스가 동기화된 해시 테이블인 것처럼 보일 수 있지만 두 가지 중요한 차이점이 있습니다. 첫째, multiton은 클라이언트가 매핑을 추가하는 것을 허용하지 않습니다. 둘째, multiton은 null 또는 빈 참조를 반환하지 않습니다. 대신 연결된 키를 사용하여 첫 번째 요청에서 multiton 인스턴스를 만들고 저장합니다. 동일한 키를 가진 후속 요청은 원래 인스턴스를 반환합니다. 해시 테이블은 구현 세부 사항 일뿐 가능한 유일한 접근 방식은 아닙니다. 이 패턴은 애플리케이션에서 공유 객체 검색을 단순화합니다.

객체 풀은 인스턴스가 아닌 클래스와 연결된 멤버이므로 한 번만 생성되기 때문에 multiton은 트리 구조로 진화하는 대신 플랫 동작을 유지합니다.

multiton은 풀의 각 멀티 톤 인스턴스가 자체 상태를 가질 수 있는 multiton의 단일 디렉토리 (즉, 모든 키가 그 자체로 동일한 네임 스페이스에 있음)에 대한 중앙 집중식 액세스를 제공한다는 점에서 고유합니다. 이러한 방식으로 패턴은 시스템에 대한 필수 개체의 색인화된 저장소 (예 : LDAP 시스템에서 제공)를 옹호합니다. 그러나 multiton은 분산 시스템이 아닌 단일 시스템에서 광범위하게 사용되도록 제한됩니다.

## 단점

Singleton 패턴과 같은 이 패턴은 애플리케이션에 전역 상태를 도입하므로 단위 테스트를 훨씬 더 어렵게 만듭니다.

가비지 수집 언어를 사용하면 객체에 대한 전역적인 강력한 참조를 도입하므로 메모리 누수의 원인이 될 수 있습니다.

## 구현

Java에서는 인스턴스에 해당하는 유형의 값과 함께 열거 유형을 사용하여 multiton 패턴을 구현할 수 있습니다. 단일 값이 있는 열거 형의 경우 singleton 패턴을 제공합니다.

C#에서는 다음 예제와 같이 열거 형을 사용할 수도 있습니다.

```C#
using System.Collections.Generic;

public enum MultitonType {
    Zero,
    One,
    Two
};

public class Multiton {
    private static readonly IDictionary<MultitonType, Multiton> instances =
        new Dictionary<MultitonType, Multiton>();
    private int number;

    private Multiton(int number) {
        this.number = number;
    }

    public static Multiton getInstance(MultitonType type) {
        // Lazy init (not thread safe as written)
        // Recommend using Double Check Locking if needing thread safety
        if (!instances.ContainsKey(type)) {
            instances.Add(type, new Multiton((int)type));
        }
        return instances[type];
    }

    public override string toString() {
        return "My number is " + number.ToString();
    }

    // Sample usage
    public static void main(string[] args) {
        Multiton m0 = Multiton.GetInstance(MultitonType.Zero);
        Multiton m1 = Multiton.GetInstance(MultitonType.One);
        Multiton m2 = Multiton.GetInstance(MultitonType.Two);
        System.Console.WriteLine(m0);
        System.Console.WriteLine(m1);
        System.Console.WriteLine(m2);
    }
}
```
