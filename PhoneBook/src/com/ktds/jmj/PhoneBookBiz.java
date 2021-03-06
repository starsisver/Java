package com.ktds.jmj;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * 전화번호부가 가져야하는 기능들을 정의한다.
 * 
 * Create : 생성
 * Read   : 찾기 혹은 상세보기
 * Update : 수정하기
 * Delete : 삭제하기
 * =======
 * CRUD라고 한다. 
 * 
 * @author jmj
 *
 */
public class PhoneBookBiz {
	
	private List<PhoneInfoVO> phoneInfoList;
	private PhoneInfoVO phoneInfo;
	private Scanner input;
	private boolean result;
	
	
	public PhoneBookBiz() {
		this.phoneInfoList = new ArrayList<PhoneInfoVO>();
		this.input = new Scanner(System.in);
	}

	public List<PhoneInfoVO> getPhoneInfoList() {
		return phoneInfoList;
	}

	public void setPhoneInfoList(List<PhoneInfoVO> phoneInfoList) {
		this.phoneInfoList = phoneInfoList;
	}
	
	/**
	 * 프로그램 실행부분
	 */
	public void startProgram(){
		
		int choice = 0;
		String searchName = "";
		String deleteName = "";
		
		//shift+tab tab당기기
		while( true ) {
			this.phoneInfo = new PhoneInfoVO();
			
			System.out.println("등록하려면 1번, 찾기하려면 2번, 삭제하려면 3번, 수정하려면 4번");
			
			try{
				choice = input.nextInt();
			}
			catch(InputMismatchException ime) {
				System.out.println("잘못입력했습니다.");
				startProgram();
			}
			
			if( choice == 1 ) {
				phoneInfo = new PhoneInfoVO();
				System.out.println("이름과 전화번호 나이 주소를 순서대로 입력하세요.");
		
				String name = input.next();
				phoneInfo.setName( name );
				phoneInfo.setPhoneNumber( input.next() );
				phoneInfo.setAge( input.nextInt() );
				phoneInfo.setAddress( input.nextLine() ); // nextInt다음에 나와서 엔터치면 무시된다.
				phoneInfo.setAddress( input.nextLine() ); // 그래서 똑같은거 또 넣음...

				this.addNewPhoneBookInfo(phoneInfo);
			}
			else if ( choice == 2 ) {
				while(true){
					System.out.println("검색할 이름을 입력하세요.");
					searchName = input.next();
					if(checkScanner("^[가-힣a-zA-Z]*$", searchName)){ // 정규표현식 이용
					}
					else{
						if (this.result = this.getPhoneInfoByName(searchName) ){
							System.out.println("찾았다.");
							printInfo();
						}
						else {
							System.out.println("없다.");
						}
						break;						
					}
				}
			}
			else if ( choice == 3 ) {
				System.out.println("삭제할 이름을 입력하세요.");
				while(true){
					deleteName = input.next();
					if (checkScanner("^[가-힣a-zA-Z]*$", deleteName)){
					}
					else{
						if ( this.result = this.deletePhoneInfoByName(deleteName)){
							this.phoneInfoList.remove(getInfo(deleteName));
							System.out.println("삭제했습니다.");
							break;
						}
						else{
							System.out.println("없습니다.");
							break;
						}
						//this.deletePhoneInfoByName(deleteName);
					}
				}
			}
		else if ( choice == 4 ) {
				while(true){
					System.out.println("1:이름수정 2:번호수정 3:나이수정 4:주소수정");
					try{
						int what = input.nextInt();
						this.setPhoneInfoByWhatItem(what);
						break;
					}
					catch(InputMismatchException ime) {
						System.out.println("잘못입력했습니다.");
					}
				}
			}
			else {
				System.out.println("다시입력해주세요.");
			}
		}
	}

	/**
	 * 스캐너할때 오류있는지 체크
	 * @param checkScan 정규표현식
	 * @param whatString 체크할 문자열
	 * @return
	 */
	public boolean checkScanner( String checkScan, String whatString ) {
		if ( !whatString.matches(checkScan) ) {
			System.out.println("재입력");
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Create
	 * @param phoneInfo
	 */
	public void addNewPhoneBookInfo(PhoneInfoVO phoneInfo) {
		this.phoneInfoList.add(phoneInfo);
	}

	/**
	 * Read
	 * @param name
	 * @return
	 */
	public boolean getPhoneInfoByName( String name ) {
		//이름으로 사람을 검색함
		for ( PhoneInfoVO phoneInfo : this.phoneInfoList ) {
			if ( phoneInfo.getName().equals(name) ){
				this.phoneInfo = phoneInfo;
				return true;
			}
		}
		return false;
	}
	
	public void printInfo(){
		System.out.println(this.phoneInfo.getName());
		System.out.println(this.phoneInfo.getPhoneNumber());
		System.out.println(this.phoneInfo.getAge());
		System.out.println(this.phoneInfo.getAddress());
	}
	/**
	 * Delete
	 * @param name
	 * @return
	 */
	public boolean deletePhoneInfoByName( String name ) {
		for ( PhoneInfoVO phoneInfo : this.phoneInfoList ) {
			if( phoneInfo.getName().equals(name) ) {
				return true;
				//http://suein1209.tistory.com/323 ConcurrentModificationException에러발생
			}
		}
		return false;
	}
	
	/**
	 * 원하는 이름의 정보를 찾을 때
	 * @param name
	 * @return
	 */
	public PhoneInfoVO getInfo( String name ) {
		for ( PhoneInfoVO phoneInfo : this.phoneInfoList ) {
			if ( phoneInfo.getName().equals(name) ){
				this.phoneInfo = phoneInfo;
				return phoneInfo;
			}
		}
		return new PhoneInfoVO();
	}

/*	=====================================================================================================
 	/**
	 * 수정할때 어떤 것을 수정할 건지 결정
	 * 1:이름 2:번호 3:나이 4:주소
	 * @param Choice
	 */
	public void setPhoneInfoByWhatItem(int Choice){

		String oldName = "";
		String newName = "";
		
		String oldNumber = "";
		String newNumber = "";
		
		String oldAddress = "";
		String newAddress = "";
		
		int oldAge = 0;
		int newAge = 0;
		
		Scanner input = new Scanner(System.in);
		
		/**
		 * 1: 이름 2:번호 3:나이 4:주소
		 **/
		while(true) {
			if (Choice == 1) {
				System.out.println("기존의 이름을 입력하세요.");
				oldName = input.next();
				if (checkScanner("^[가-힣a-zA-Z]*$", oldName)){
				}
				else{
					System.out.println("새로운 이름을 입력하세요.");
					newName = input.next();
					if (checkScanner("^[가-힣a-zA-Z]*$", newName)){
					}
					else{
						setPhoneInfoByName(oldName, newName);
						break;
					}
				}
			}
			else if (Choice == 2) {
				System.out.println("기존의 번호를 입력하세요.");
				oldNumber = input.next();
				System.out.println("새로운 번호를 입력하세요.");
				newNumber = input.next();
				
				setPhoneInfoByPhoneNumber(oldNumber, newNumber);
				break;
			}
			else if (Choice == 3) {
				System.out.println("기존의 나이를 입력하세요.");
				oldAge = input.nextInt();
				System.out.println("새로운 나이를 입력하세요.");
				newAge = input.nextInt();
				
				setPhoneInfoByAge(oldAge,newAge);
				break;
			}
			else if (Choice == 4) {
				System.out.println("기존의 주소를 입력하세요.");
				oldAddress = input.next();
				System.out.println("새로운 주소를 입력하세요.");
				newAddress = input.next();
				
				setPhoneInfoByAddress(oldAddress, newAddress);
				break;
			}
			else {
				System.out.println("다시입력하세요.");
			}
		}
	}

	/**
	 * Update By Name
	 * @param oldName 이전 이름
	 * @param newName 새로운 이름
	 * @return
	 */
	public PhoneInfoVO setPhoneInfoByName( String oldName, String newName) {
		for ( PhoneInfoVO phoneInfo : this.phoneInfoList ) {
			if( phoneInfo.getName().equals(oldName) ) {
				phoneInfo.setName(newName);
				System.out.println("바꿨다");
				return  phoneInfo;
			}
			else {
				System.out.println("없다.");
			}
		}
		return new PhoneInfoVO();
	}
	
	/**
	 * Update By Number
	 * @param oldName 이전 이름
	 * @param newName 새로운 이름
	 * @return
	 */
	public PhoneInfoVO setPhoneInfoByPhoneNumber( String oldNumber, String newNumber) {
		for ( PhoneInfoVO phoneInfo : this.phoneInfoList ) {
			if( phoneInfo.getPhoneNumber().equals( oldNumber) ) {
				phoneInfo.setPhoneNumber(newNumber);
				System.out.println("바꿨다");
				return  phoneInfo;
			}
			else {
				System.out.println("없다.");
			}
		}
		return new PhoneInfoVO();
	}
	
	/**
	 * Update By Age
	 * @param oldName 이전 이름
	 * @param newName 새로운 이름
	 * @return
	 */
	public PhoneInfoVO setPhoneInfoByAge( int oldAge, int newAge) {
		for ( PhoneInfoVO phoneInfo : this.phoneInfoList ) {
			if( phoneInfo.getAge() == oldAge ) {
				phoneInfo.setAge(newAge);
				System.out.println("바꿨다");
				return  phoneInfo;
			}
			else {
				System.out.println("없다.");
			}
		}
		return new PhoneInfoVO();
	}
	
	/**
	 * Update By Address
	 * @param oldName 이전 이름
	 * @param newName 새로운 이름
	 * @return
	 */
	public PhoneInfoVO setPhoneInfoByAddress( String oldAddress, String newAddress) {
		for ( PhoneInfoVO phoneInfo : this.phoneInfoList ) {
			if( phoneInfo.getAddress().equals(oldAddress) ) {
				phoneInfo.setAddress(newAddress);
				System.out.println("바꿨다");
				return  phoneInfo;
			}
			else {
				System.out.println("없다.");
			}
		}
		return new PhoneInfoVO();
	}
	


}
