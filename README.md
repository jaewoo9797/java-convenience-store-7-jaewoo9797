## 기능 요구 사항
구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템을 구현한다.
<br>
- 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
- 총구매액은 상품별 가격과 수량을 곱하여 계산하며, 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다.
- 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.
- 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 선택할 수 있다.
- 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
- Exception이 아닌 IllegalArgumentException, IllegalStateException 등과 같은 명확한 유형을 처리한다.
### 재고 관리
- 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
- 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.
### 프로모션 할인
- 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.   

- 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.   

- 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.  

- 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.     

- 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.   

- 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.   

- 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.     
### 멤버십 할인
- 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- 멤버십 할인의 최대 한도는 8,000원이다.

### 영수증 출력

- 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.
- 영수증 항목은 아래와 같다.
- 구매 상품 내역: 구매한 상품명, 수량, 가격
- 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록

- 금액 정보   
  - 총구매액: 구매한 상품의 총 수량과 총 금액
  - 행사할인: 프로모션에 의해 할인된 금액
  - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
  - 내실돈: 최종 결제 금액
- 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.


# 기능 명세서 작성
**주문**
- 사용자가 입력한 상품을 주문한다.
  - 주문된 상품의 가격과 수량을 기반으로 주문을 생성한다.
  - 주문한 수량이 숫자가 아니라면 에러가 발생한다.
- 주문이 발생한 날짜를 저장한다.

**상품**
  - 상품은 이름, 가격, 재고, 프로모션을 가진다.
  - 상품의 재고를 주문수량과 비교한다.
    - 주문한 상품의 수량이 재고를 넘는지 검사한다.
    - 프로모션에 따른 가격을 계산한다.
    - 프로모션에 따른 재고감소를 계산한다.
      - 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 혜택에 대한 안내 메시지를 출력한다.
      - 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메시지를 출력한다.
  - 주문의 수량만큼 재고가 줄어든다.
    - 주문이 취소되면 재고가 줄어든 수량만큼 늘어난다.
  - 프로모션에 따라 혜택이 주어진다.
    - 2+1
    - 1+1
      - 혜택에 따른 결제 금액에서 차감할 값을 반환한다.

  
**상품 저장소**
- 상품을 저장한다.
- 주문받은 상품을 찾는다.
- 주문받은 상품의 재고를 확인한다.
  - 주문한 상품의 재고가 부족하면 에러가 발생한다.
- 상품을 제공한다.
  - 제공된 상품의 개수만큼 감소시킨다.
  - 구매하지 않을 경우 개수를 다시 증가시킨다.
- 최종 결제 금액을 계산한다.

**할인**
- 프로모션 할인
  - MD 추천 (1+1)
  - 탄산2+1
  - 반짝할인 (1+1)
- 조건
  - 프로모션 기간인가?
  - 재고가 충분한가?
- 멤버쉽 할인
  - 구매하는 품목이 프로모션 제품인가 확인한다.
  - 프로모션 가격을 제외한 후 할인을 적용한다.
- 멤버십 할인의 최대 한도는 8,000원이다.

**영수증**
- 구매 내역과 할인을 요약하여 출력한다.
  - 구매 상품 내역 : 구매한 상품명, 수량, 가격
  - 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
- 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.

**금액**
- 총구매액: 구매한 상품의 총 수량과 총 금액을 구한다.
  - 행사할인: 프로모션에 의해 할인된 금액을 구한다.
  - 멤버십할인: 멤버십에 의해 추가로 할인된 금액을 구한다.
  - 내실돈: 최종 결제 금액을 구한다.
 
**출력**
- 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.
- 추가 구매를 진행할지 또는 종료할지를 입력받는다.
  - 추가 구매(Y) 선택 시 주문을 받는다.
  - 추가 구매(N) 선택 시 애플리케이션을 종료한다.



### 요구사항
**라이브러리**
- `camp.nextstep.edu.missionutils`에서 제공하는 `DateTimes` 및 `Console` API를 사용하여 구현해야 한다.
  - 현재 날짜와 시간을 가져오려면 `camp.nextstep.edu.missionutils.DateTimes`의 `now()`를 활용한다.
  - 사용자가 입력하는 값은 `camp.nextstep.edu.missionutils.Console`의 `readLine()`을 활용한다.

상수 관리