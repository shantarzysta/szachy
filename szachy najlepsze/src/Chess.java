import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

//Tablica figury znaja swoje miejsce a nie tablica jedna wielka 
//tablica tylko do drukowania na ekran


//lista czy sztywne tworzenie 8 obiektow 
//lista to automatyzacja jakas ale i problemy skladni
//sztywno to kilometr kodu ale wszystko ladnie widac i latwo, ale duzo wszystko recznie  <==x 


//jak sprawdzac szach
//jak sprawdzac czy nie ma czegos po drodze
//w zaleznosci od figury w bedzie sprawdzanie tablicy czy nie ma czegos po drodze, metoda do pieces bishop np 


public class Chess {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scanIn = new Scanner(System.in);
		
		System.out.println("Jak chcesz nazwac swoj save?");
		
		PrintWriter saver = new PrintWriter(scanIn.nextLine());
		
		///*
		
		Pieces[] white = new Pieces[16];
		Pieces[] black = new Pieces[16];
//###################################################
		for (int i = 0; i < 8; i++) {
			white[i] = new Pawn(true);
		}
		
		white[8] = new Knight(true);
		white[9] = new Knight(true);
		
		white[10] = new Bishop(true);
		white[11] = new Bishop(true);
		
		white[12] = new Rook(true);
		white[13] = new Rook(true);
		
		white[14] = new Queen(true);
		
		white[15] = new King(true);
//######################################################3
		for (int i = 0; i < 8; i++) {
			black[i] = new Pawn(true);
		}
		
		black[8] = new Knight(false);
		black[9] = new Knight(false);
		
		black[10] = new Bishop(false);
		black[11] = new Bishop(false);
		
		black[12] = new Rook(false);
		black[13] = new Rook(false);
		
		black[14] = new Queen(false);
		
		black[15] = new King(false);
		//*/
		
		Pieces.setWhite(white);
		
		ShowBoard Board = new ShowBoard();
		Board.setPosition();
		Board.showMe();
		
		boolean whiteIsInCheck = false;
		boolean blackIsInCheck = false;
		boolean checkmate = false;
		int x0, y0, x1, y1, lap=1, k=0;
		char cx0, cx1, f, a;
		String m, stop;
		
		while(lap>0 && checkmate==false) {
			if(lap%2==1) {
				m = scanIn.nextLine();
				System.out.println("Ruch bialych:");
				fixType(m,scanIn);
				
				f = m.charAt(0);
				f = Character.toUpperCase(f);
				checkF(f,scanIn);
				
				cx0 = m.charAt(1);
				checkX(cx0,scanIn);
				
				y0 = Character.getNumericValue(m.charAt(2));
				checkY(y0,scanIn);
				
				cx1 = m.charAt(3);
				checkX(cx1,scanIn);
				
				y1 = Character.getNumericValue(m.charAt(4));
				checkY(y1,scanIn);
				
				
				
				f = Character.toUpperCase(f);
				saver.print(f);
				
				saver.print(cx0);
				x0 = charFix(cx0);	
				
				saver.print(y0);
				y0 = intFix(y0);
				
				saver.print(cx1);
				x1 = charFix(cx1);
				
				saver.print(y1);
				y1 = intFix(y1);
				
				if(m.length()==6) {
					a = m.charAt(5); // action key
					checkA(a,scanIn);
					saver.print(a);
				}
				else {
					a = 'n';
				}
				saver.println();
				
				for (int j = 0; j < white.length; j++) {
					if(white[j].posX==x0 && white[j].posY==y0) {
						if(f=='P') {
							if(a=='x') {
								((Pawn)white[j]).pawnCapture(x0, x1, y0, y1);
							}
							else {
								((Pawn)white[j]).move(x0, x1, y0, y1);
							}
							
							((Pawn)white[j]).noWay(y0, y1, white);
							((Pawn)white[j]).noWay(y0, y1, black);
						}
						else if(f=='N') {
							((Knight)white[j]).move(x0, x1, y0, y1);
							((Knight)white[j]).noWay(x0, x1, y0, y1, white);
							((Knight)white[j]).noWay(x0, x1, y0, y1, black);
						}
						else if(f=='B') {
							((Bishop)white[j]).move(x0, x1, y0, y1);
							((Bishop)white[j]).noWay(x0, x1, y0, y1, white);
							((Bishop)white[j]).noWay(x0, x1, y0, y1, black);
						}
						else if(f=='R') {
							((Rook)white[j]).move(x0, x1, y0, y1);
							((Rook)white[j]).noWay(x0, x1, y0, y1, white);
							((Rook)white[j]).noWay(x0, x1, y0, y1, black);
						}
						else if(f=='Q') {
							((Queen)white[j]).move(x0, x1, y0, y1);
							((Queen)white[j]).noWay(x0, x1, y0, y1, white);
							((Queen)white[j]).noWay(x0, x1, y0, y1, black);
						}
						else if(f=='K') {
							if(a=='c') {
								((King)white[j]).castleMeShort();
							}
							else if(a=='l') {
								((King)white[j]).castleMeLong();
							}
							else {
								((King)white[j]).move(x0, x1, y0, y1);	
							}
							((King)white[j]).noWay(x1,y1,white);
							((King)white[j]).noWay(x1,y1,black);
						}
					}
				}
				whiteIsInCheck = isCheck((King)white[15], white);
				if(whiteIsInCheck==true) {
					System.out.println("Biale sa w szachu po wlasnym ruchu! Koniec gry!");
					checkmate=true;
				}
				blackIsInCheck = isCheck((King)black[15], black);
				if(blackIsInCheck==true) {
					System.out.println("Szach na czarnym krolu!");
				}
			}
			else {
				System.out.println("Ruch czarnych:");
				m = scanIn.nextLine();
				fixType(m,scanIn);
				
				f = m.charAt(0);
				f = Character.toUpperCase(f);
				checkF(f,scanIn);
				
				cx0 = m.charAt(1);
				checkX(cx0,scanIn);
				
				y0 = Character.getNumericValue(m.charAt(2));
				checkY(y0,scanIn);
				
				cx1 = m.charAt(3);
				checkX(cx1,scanIn);
				
				y1 = Character.getNumericValue(m.charAt(4));
				checkY(y1,scanIn);
				
				
				
				f = Character.toUpperCase(f);
				saver.print(f);
				
				saver.print(cx0);
				x0 = charFix(cx0);	
				
				saver.print(y0);
				y0 = intFix(y0);
				
				saver.print(cx1);
				x1 = charFix(cx1);
				
				saver.print(y1);
				y1 = intFix(y1);
				
				if(m.length()==6) {
					a = m.charAt(5); // action key
					checkA(a,scanIn);
					saver.print(a);
				}
				else {
					a = 'n';
				}
				saver.println();
				
				for (int j = 0; j < black.length; j++) {
					if(black[j].posX==x0 && black[j].posY==y0) {
						if(f=='P') {
							if(a=='x') {
								((Pawn)black[j]).pawnCapture(x0, x1, y0, y1);
							}
							else {
								((Pawn)black[j]).move(x0, x1, y0, y1);
							}
							
							((Pawn)black[j]).noWay(y0, y1, white);
							((Pawn)black[j]).noWay(y0, y1, black);
						}
						else if(f=='N') {
							((Knight)black[j]).move(x0, x1, y0, y1);
							((Knight)black[j]).noWay(x0, x1, y0, y1, white);
							((Knight)black[j]).noWay(x0, x1, y0, y1, black);
						}
						else if(f=='B') {
							((Bishop)black[j]).move(x0, x1, y0, y1);
							((Bishop)black[j]).noWay(x0, x1, y0, y1, white);
							((Bishop)black[j]).noWay(x0, x1, y0, y1, black);
						}
						else if(f=='R') {
							((Rook)black[j]).move(x0, x1, y0, y1);
							((Rook)black[j]).noWay(x0, x1, y0, y1, white);
							((Rook)black[j]).noWay(x0, x1, y0, y1, black);
						}
						else if(f=='Q') {
							((Queen)black[j]).move(x0, x1, y0, y1);
							((Queen)black[j]).noWay(x0, x1, y0, y1, white);
							((Queen)black[j]).noWay(x0, x1, y0, y1, black);
						}
						else if(f=='K') {
							if(a=='c') {
								((King)black[j]).castleMeShort();
							}
							else if(a=='l') {
								((King)black[j]).castleMeLong();
							}
							else {
								((King)black[j]).move(x0, x1, y0, y1);	
							}
							((King)black[j]).noWay(x1,y1,white);
							((King)black[j]).noWay(x1,y1,black);
						}
					}
				}
				whiteIsInCheck = isCheck((King)white[15], white);
				if(whiteIsInCheck==true) {
					System.out.println("Szach na bialym krolu!");
				}
				blackIsInCheck = isCheck((King)black[15], black);
				if(blackIsInCheck==true) {
					System.out.println("Czarne sa w szachu po wlasnym ruchu! Koniec gry!");
					checkmate=true;
				}
			}

			Board.changePosition(x0, x1, y0, y1);
			Board.showMe();
			lap++;
			saver.flush();
			if(checkmate==false) {
				System.out.println("Czy zakonczyc gre? [T/n]");
				stop=scanIn.nextLine();
				if(stop.charAt(0)=='T') {
					System.out.println("1.Remis \n2.Poddanie");
					k=Integer.parseInt(scanIn.nextLine());
					if(k==1) {
						System.out.println("Partia zakonczona remisem");
						lap=0;
					}
					else {
						if(lap%2==1) {
							System.out.println("Biale poddaly partie");
							lap=0;
						}
						else {
							System.out.println("Czarne poddaly partie");
							lap=0;
						}
					}
				}
			}
		}
		scanIn.close();
		saver.close();		
	}
	static boolean isCheck(Pieces King, Pieces[] box) {
//####################################################################Pawns
		for (int i = 0; i < box.length; i++) {
			if(King.posX==box[i].posX-1 || King.posX==box[i].posX+1) {
				if(King.posY==box[i].posY) {
					if(box[i].iAm=='P') {
						return true;
					}
				}
			}
//#####################################################################Columns and rows
			for (int j = 0; j < 8; j++) {
				if(King.posX==box[i].posX-j && King.posX==box[i].posX+j) {
					if(box[i].iAm=='R' || box[i].iAm=='Q') {
						return true;
					}
					else {
						break;
					}
				}
			}
			for (int j = 0; j < 8; j++) {
				if(King.posY==box[i].posY-j || King.posY==box[i].posY+j) {
					if(box[i].iAm=='R' || box[i].iAm=='Q') {
						return true;
					}
					else {
						break;
					}
				}
			}
//####################################################################Diagonals
			for (int j = 0; j < 8; j++) {
				if(King.posX==box[i].posX-j && King.posY==box[i].posY-j ) {
					if(box[i].iAm=='B' || box[i].iAm=='Q') {
						return true;
					}
					else {
						break;
					}
				}
			}
			for (int j = 0; j < 8; j++) {
				if(King.posX==box[i].posX+j && King.posY==box[i].posY+j ) {
					if(box[i].iAm=='B' || box[i].iAm=='Q') {
						return true;
					}
					else {
						break;
					}
				}
			}
			for (int j = 0; j < 8; j++) {
				if(King.posX==box[i].posX-j && King.posY==box[i].posY+j ) {
					if(box[i].iAm=='B' || box[i].iAm=='Q') {
						return true;
					}
					else {
						break;
					}
				}
			}
			for (int j = 0; j < 8; j++) {
				if(King.posX==box[i].posX+j && King.posY==box[i].posY-j ) {
					if(box[i].iAm=='B' || box[i].iAm=='Q') {
						return true;
					}
					else {
						break;
					}
				}
			}
//####################################################################Knights
			if(King.posX==box[i].posX-2 && King.posY==box[i].posY+1) {
				if(box[i].iAm=='N') {
					return true;
				}
			}
			if(King.posX==box[i].posX-1 && King.posY==box[i].posY+2) {
				if(box[i].iAm=='N') {
					return true;
				}
			}
			if(King.posX==box[i].posX+1 && King.posY==box[i].posY+2) {
				if(box[i].iAm=='N') {
					return true;
				}
			}
			if(King.posX==box[i].posX+2 && King.posY==box[i].posY+1) {
				if(box[i].iAm=='N') {
					return true;
				}
			}
			if(King.posX==box[i].posX+2 && King.posY==box[i].posY-1) {
				if(box[i].iAm=='N') {
					return true;
				}
			}
			if(King.posX==box[i].posX+1 && King.posY==box[i].posY-2) {
				if(box[i].iAm=='N') {
					return true;
				}
			}
			if(King.posX==box[i].posX-1 && King.posY==box[i].posY-2) {
				if(box[i].iAm=='N') {
					return true;
				}
			}
			if(King.posX==box[i].posX-2 && King.posY==box[i].posY-1) {
				if(box[i].iAm=='N') {
					return true;
				}
			}
		}
		return false;
	}
	static void killF(int i, Pieces[] box) {
		box[i]=null;
	}
	
	static void fixType(String m, Scanner scanIn) {
		while(Character.isDigit(m.charAt(0))) {
			System.out.println("Blad wpisywania figury, podano liczbe zamiast litery");
			System.out.println("Podaj notacje ruchu ponownie");
			m = scanIn.nextLine();
		}
		while(Character.isDigit(m.charAt(1))) {
			System.out.println("Blad wpisywania pola poczatkowego (a-h), podano liczbe zamiast litery");
			System.out.println("Podaj notacje ruchu ponownie");
			m = scanIn.nextLine();
		}
		while(Character.isLetter(m.charAt(2))) {
			System.out.println("Blad wpisywania pola poczatkowego (1-8), podano litere zamiast liczby");
			System.out.println("Podaj notacje ruchu ponownie");
			m = scanIn.nextLine();
		}
		while(Character.isDigit(m.charAt(3))) {
			System.out.println("Blad wpisywania pola docelowego (a-h), podano liczbe zamiast litery");
			System.out.println("Podaj notacje ruchu ponownie");
			m = scanIn.nextLine();
		}
		while(Character.isLetter(m.charAt(4))) {
			System.out.println("Blad wpisywania pola docelowego (1-8), podano litere zamiast liczby");
			System.out.println("Podaj notacje ruchu ponownie");
			m = scanIn.nextLine();
		}
		if(m.length()==6) {
			while(Character.isDigit(m.charAt(5))) {
				System.out.println("Blad wpisywania znaku akcji (x,c,l), podano liczbe zamiast litery");
				System.out.println("Podaj notacje ruchu ponownie");
				m = scanIn.nextLine();
			}
		}
		else {
			return;
		}

		return;
	}
	
	static char checkF(char f, Scanner scanIn) {
		while(f!='P' && f!='N' && f!='B' && f!='R' && f!='Q' && f!='K') {
			System.out.println("Blad wpisywania figury, wybierz figure ponownie");
			f = scanIn.nextLine().charAt(0);
			f = Character.toUpperCase(f);
		}
		return f;
	}
	
	static char checkX(char cx, Scanner scanIn) {
		
		while(cx!='a' && cx!='b' && cx!='c' && cx!='d' && cx!='e' && cx!='f' && cx!='g' && cx!='h') {
			System.out.println("Blad wpisywania a-h, podaj wspolrzedna ponownie");
			cx = scanIn.nextLine().charAt(0);
		}
		return cx;

	}
	
	static int checkY(int y, Scanner scanIn) {
			
		while (y<=0 || y>=9) {
			System.out.println("Blad wpisywania 1-8, podaj wspolrzedna ponownie");
			y = Integer.parseInt(scanIn.nextLine());
		}
		return y;
	}
	
	static char checkA(char a, Scanner scanIn) {
		while(a!='x' && a!='c' && a!='l') {
			System.out.println("Blad wpisywania akcji, podaj akcje ponownie");
			a = scanIn.nextLine().charAt(0);
		}
		return a;
	}
	
	
	static int charFix(char cx)/* zamienia litery na odpowiedni indeks oX */{
		if (cx=='a') {
			return 0;
		}
		else if(cx=='b') {
			return 1;
		}
		else if(cx=='c') {
			return 2;
		}
		else if(cx=='d') {
			return 3;
		}
		else if(cx=='e') {
			return 4;
		}
		else if(cx=='f') {
			return 5;
		}
		else if(cx=='g') {
			return 6;
		}
		else if(cx=='h') {
			return 7;
		}
		else {
			return 0;
		}
	}
	
	static int intFix(int y)/* zmienia podana wspolrzedna Y na indeks tablicy oY */ {
		y=y-1;
		return y;
	}
}

class ShowBoard {
	//zawsze patrzymy dla bialych
	//00 == a1
	char positionBoard[][];// = new char[19][8];

	public void setPosition()/* ustawia pozycje startowa szachownicy */ {
		this.positionBoard = new char[19][8];
		for (int i = 0; i < 19; i++) {
			for (int j = 2; j < 6; j++) {
				positionBoard[i][j] = ' ';
			}
			
		}
		for (int i = 0; i < 19; i++) {
			if(i<8) {
				positionBoard[i][1] = 'P';
				positionBoard[i][6] = 'P';
			}
			else if(i>10) {
				positionBoard[i][0] = 'W';
				positionBoard[i][1] = 'W';
				
				positionBoard[i][6] = 'B';
				positionBoard[i][7] = 'B';
			}
			else {
				for (int j = 0; j < 8; j++) {
					positionBoard[i][j] = ' ';
				}	
			}
		}
		positionBoard[1][0] = 'N';
		positionBoard[6][0] = 'N';
		
		positionBoard[2][0] = 'B';
		positionBoard[5][0] = 'B';
			
		positionBoard[0][0] = 'R';
		positionBoard[7][0] = 'R';
			
		positionBoard[3][0] = 'Q';
		positionBoard[4][0] = 'K';
			
		positionBoard[1][7] = 'N';
		positionBoard[6][7] = 'N';
			
		positionBoard[2][7] = 'B';
		positionBoard[5][7] = 'B';
			
		positionBoard[0][7] = 'R';
		positionBoard[7][7] = 'R';
			
		positionBoard[3][7] = 'Q';
		positionBoard[4][7] = 'K';
	}
	
	public void showMe() /* wyswietla stan szachownicy na ekran */{
		for (int i = 7; i >= 0; i--) {
			System.out.print(i+1 + "~|");
			for (int j = 0; j < 19 ; j++) {
				System.out.print(this.positionBoard[j][i]);
				if(j<8) {
					System.out.print("|");
				}
				if(j>10) {
					System.out.print("|");
				}
				if(j==10) {
					System.out.print(i+1 +"~|");
				}
			}
			
			System.out.println();
		}
		for (int j = 0; j < 7; j++) {
				System.out.print("~~~~~~");
			}
		System.out.println();
		System.out.println("  |A|B|C|D|E|F|G|H|     |A|B|C|D|E|F|G|H|");
	}
	
	public void changePosition(int x0, int x1, int y0, int y1) {
	
			positionBoard[x1][y1]=positionBoard[x0][y0];
			positionBoard[x0][y0]=' ';
			
			x0 = x0+11;
			x1 = x1+11;
			
			positionBoard[x1][y1]=positionBoard[x0][y0];
			positionBoard[x0][y0]=' ';
	}
}

class Pieces {
	public String name;
	public boolean exists = true;
	public int posX;
	public int posY;
	public int count;
	public char iAm; //done
	public boolean isWhite; //done
	Scanner scanIn = new Scanner(System.in);
//	Pieces(){
		
//	}
	
	public String retColour(boolean isWhite) {
		if(isWhite==true) {
			return "White";
		}
		else {
			return "Black";
		}
	}
	
	public static void setWhite(Pieces[] box)/* white box 0-7 - p, 8-9 - n, 10-11 - b, 12-13 - r, 14-q, 15-k, */{
//############################################### Colour
		for (int i = 0; i < box.length; i++) {
			box[i].isWhite=true;
		}
//############################################### Pawns
		for (int i = 0; i <=7 ; i++) {
			box[i].posX = i;
			box[i].posY = 1;
			box[i].name = "Pawn";
		}
//############################################### All but Pawns
		for (int i = 8; i < box.length; i++) {
			box[i].posY = 0;
		}
//############################################### Knights
		box[8].posX = 1;
		box[9].posX = 6;
		box[8].name = "Knight";
		box[9].name = "Knight";
//############################################### Bishops
		box[10].posX = 2;
		box[11].posX = 5;
		box[10].name = "Bishop";
		box[11].name = "Bishop";
//############################################### Rooks
		box[12].posX = 0;
		box[13].posX = 7;
		box[12].name = "Rook";
		box[13].name = "Rook";
//############################################### Queen
		box[14].posX = 3;
		box[14].name = "Queen";
//############################################### King
		box[15].posX = 4;
		box[15].name = "King";
	}
	
	public static void setBlack(Pieces[] box)/* black box 0-7 - p, 8-9 - n, 10-11 - b, 12-13 - r, 14-q, 15-k, */{
//################################################ Colour
		for (int i = 0; i < box.length; i++) {
			box[i].isWhite = false;
		}
//################################################ Pawns
		for (int i = 0; i <=7 ; i++) {
			box[i].posX = i;
			box[i].posY = 6;
			box[i].name = "Pawn";
		}
//############################################### All but Pawns
		for (int i = 8; i < box.length; i++) {
			box[i].posY = 7;
		}
//############################################### Knights
		box[8].posX = 1;
		box[9].posX = 6;
		box[8].name = "Knight";
		box[9].name = "Knight";
//############################################### Bishops
		box[10].posX = 2;
		box[11].posX = 5;
		box[10].name = "Bishop";
		box[11].name = "Bishop";
//############################################### Rooks
		box[12].posX = 0;
		box[13].posX = 7;
		box[12].name = "Rook";
		box[13].name = "Rook";
//############################################### Queen
		box[14].posX = 4;
		box[14].name = "Queen";
//############################################### King
		box[15].posX = 3;
		box[15].name = "King";
	}
	
	public Integer setCount(char iAm)/* dla duzych liter */ {
		switch (iAm) {
		//metoda dostaje char z oznaczeniem figury > ascii zwraca ilosc figur na poczatku nie wiem po co, przeciez generuje sie samo ustawienie pocz
		case 80:
			return 8;
		
		case 78:
			return 2;
			
		case 66:
			return 2;
			
		case 82:
			return 2;
		
		case 81:
			return 1;
			
		case 75:
			return 1;
			
		default:
			return 0;
		}
	}
}
	
class Pawn extends Pieces{
	public boolean firstMove;
	
	Pawn(boolean isWhite){
		this.isWhite = isWhite;
		firstMove = true;
		iAm = 'P';
	}
	boolean noWay(int y0, int y1, Pieces[] box) {
		if(y0+1==y1) {
			for (int i = 0; i < box.length; i++) {
				if(box[i].posY==y1) {
					System.out.println("Cos stoi na drodze piona! : "+box[i].name + " " + box[i].retColour(box[i].isWhite));
					return true;
				}
			}
		}
		else {
			for (int i = 0; i < box.length; i++) {
				if(box[i].posY==y1) {
					System.out.println("Cos stoi na drodze piona! : "+box[i].name + " " + box[i].retColour(box[i].isWhite));
					return true;
				}
			}
		}
		return false;
	}
	void move(int x0, int x1, int y0, int y1) {
		if(posX==x0 && posY==y0) {
			if(x0==x1) {
				if(firstMove==true) {
					if(x1==x0+1 || x1==x0+2) {
						posY = y1;
						firstMove = false;
						System.out.println("Pawn condition 1 succes");
						return;
					}
					else {
						System.out.println("Wykonano ruch o wiecej niz dwa pola : error Pawn move cond.1");
						return;
					}
				}
				else {
					if(x1==x0+1) {
						posY = y1;
						System.out.println("Pawn condition 2 succes");
						return;
					}
					else {
						System.out.println("Wykonano ruch o wiecej niz jedno pole : error Pawn move cond.2");
						return;
					}
				}
			}
			else {
				System.out.println("Wykonano ruchu w oX pionem! : error Pawn move cond.3");
				return;
			}
		}
		else {
			System.out.println("Nie podnosisz odpowiedniej figury : error Pawn move cond.4");
			return;
		}
	}
	void pawnCapture(int x0, int x1, int y0, int y1) {
		if(posX==x0 && posY==y0) {
			if(y1==y0+1) {
				if(x1==x0+1 || x1==x0-1) {
					posX=x1;
					posY=y1;
				}
				else {
					System.out.println("Error : Pawn cond.3, nie bijesz pionem na skos");
				}
			}
			else {
				System.out.println("Error : Pawn cond.2, nie bijesz pionem do przodu");
			}
		}
		else {
			System.out.println("Error : Pawn cond.0, podnosisz nieodpowednią figurą");
		}
	}
}

class Knight extends Pieces{
	
	Knight(boolean isWhite){
		this.isWhite = isWhite;
		iAm = 'N';
	}
	boolean noWay(int x0, int x1, int y0, int y1, Pieces[] box) {
		for (int i = 0; i < box.length; i++) {
			if(box[i].posX==x1 && box[i].posY==y1) {
				System.out.println("Cos stoi na miejscu docelowym konia! : "+box[i].name + " " + box[i].retColour(box[i].isWhite));
				return true;
			}
		}
		return false;
	}
	void move(int x0, int x1, int y0, int y1) {
		if(posX==x0 && posY==y0) {
			if(x1==x0-1 && y1==y0+2) {
				posX = x1;
				posY = y1;
				System.out.println("Knight condition 1 succes");
				return;
			}
			else if(x1==x0-1 && y1==y0-2) {
				posX = x1;
				posY = y1;
				System.out.println("Knight condition 2 succes");
				return;
			}
			else if(x1==x0+1 && y1==y0+2) {
				posX = x1;
				posY = y1;
				System.out.println("Knight condition 3 succes");
				return;
			}
			else if(x1==x0+1 && y1==y0-2) {
				posX = x1;
				posY = y1;
				System.out.println("Knight condition 4 succes");
				return;
			}
			else if(x1==x0+2 && y1==y0+1) {
				posX = x1;
				posY = y1;
				System.out.println("Knight condition 5 succes");
				return;
			}
			else if(x1==x0-2 && y1==y0+1) {
				posX = x1;
				posY = y1;
				System.out.println("Knight condition 6 succes");
				return;
			}
			else if(x1==x0+2 && y1==y0-1) {
				posX = x1;
				posY = y1;
				System.out.println("Knight condition 7 succes");
				return;
			}
			else if(x1==x0-2 && y1==y0-1) {
				posX = x1;
				posY = y1;
				System.out.println("Knight condition 8 succes");
				return;
			}
			else {
				System.out.println("Wykonano niepoprawny ruch : error Knight move cond.1");
				return;
			}
		}
		else {
			System.out.println("Nie podnosisz odpowiedniej figury : error Knight move cond.2");
			return;
		}
	}
}

class Bishop extends Pieces{
	
	Bishop(boolean isWhite){
		this.isWhite = isWhite;
		iAm = 'B';
	}
	boolean noWay(int x0, int x1, int y0, int y1, Pieces[] box) {
		if (x0 < x1 && y0 < y1) {
			for (int i = y0; i <= y1; i++) {
				for (int j = x0; j <= x1; j++) {
					for (int j2 = 0; j2 < box.length; j2++) {
						if (box[j2].posY == i && box[j2].posX == j) {
							System.out.println("Cos stoi na drodze gonca! : " + box[j2].name + " "
									+ box[j2].retColour(box[j2].isWhite) + " skos++");
							return true;
						}
					}
				}
			}
		} else if (x0 > x1 && y0 > y1) {
			for (int i = y1; i <= y0; i++) {
				for (int j = x1; j <= x0; j++) {
					for (int j2 = 0; j2 < box.length; j2++) {
						if (box[j2].posY == i && box[j2].posX == j) {
							System.out.println("Cos stoi na drodze gonca! : " + box[j2].name + " "
									+ box[j2].retColour(box[j2].isWhite) + " skos--");
							return true;
						}
					}
				}
			}
		} else if (x0 < x1 && y0 > y1) {
			for (int i = y1; i <= y0; i++) {
				for (int j = x0; j <= x1; j++) {
					for (int j2 = 0; j2 < box.length; j2++) {
						if (box[j2].posY == i && box[j2].posX == j) {
							System.out.println("Cos stoi na drodze gonca! : " + box[j2].name + " "
									+ box[j2].retColour(box[j2].isWhite) + " skos+-");
							return true;
						}
					}
				}
			}
		} else {
			for (int i = y0; i <= y1; i++) {
				for (int j = x1; j <= x0; j++) {
					for (int j2 = 0; j2 < box.length; j2++) {
						if (box[j2].posY == i && box[j2].posX == j) {
							System.out.println("Cos stoi na drodze gonca! : " + box[j2].name + " "
									+ box[j2].retColour(box[j2].isWhite) + " skos-+");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	void move(int x0, int x1, int y0, int y1) {
		if(posX==x0 || posY==y0) {
			if((x0<x1 & y0<y1) | (x0>x1 & y0>y1)) {
				if(x0+x1==y0+y1) {
					posX=x1;
					posY=y1;
					System.out.println("Bishop condition 1 succes");
					return;
				}
				else {
					System.out.println("Wykonano niepoprawny ruch : error Bishop move cond.1");
					return;
				}
			}
			else if((x0>x1 & y0<y1) | (x0<x1 & y0>y1)) {
				if(x0+y0==x1+y1) {
					posX=x1;
					posY=y1;
					System.out.println("Bishop condition 2 succes");
					return;
				}
				else {
					System.out.println("Wykonano niepoprawny ruch : error Bishop move cond.2");
					return;
				}
			}
		}
		else {
			System.out.println("Nie podnosisz odpowiedniej figury : error Bishop move cond.3");
			return;
		}
	}
}

class Rook extends Pieces{
	public boolean canCastle;
	
	Rook(boolean isWhite){
		this.isWhite = isWhite;
		iAm = 'R';
	}
	boolean noWay(int x0, int x1, int y0, int y1, Pieces[] box) {
		if(y0==y1) {
			for (int i = x0; i <= x1; i++) {
				for (int j = 0; j < box.length; j++) {
					if(box[j].posX==i) {
						System.out.println("Cos stoi na drodze wiezy! : "+box[j].name + " " + box[j].retColour(box[j].isWhite) + " X axis");
						return true;
					}
				}
			}
		}
		else {
			for (int i = y0; i <= y1; i++) {
				for (int j = 0; j < box.length; j++) {
					if(box[j].posY==i) {
						System.out.println("Cos stoi na drodze wiezy! : "+box[j].name + " " + box[j].retColour(box[j].isWhite) + " Y axis");
						return true;
					}
				}
			}
		}
		return false;
	}
	void move(int x0, int x1, int y0, int y1) {
		if(posX==x0 || posY==y0) {
			if(posX==x0 && x0==x1) {
				posY = y1;
				System.out.println("Rook condition 1 succes");
				if(canCastle==true) {
					canCastle = false;
				}
				return;
			}
			else if(posY==y0 && y0==y1) {
				posX = x1;
				System.out.println("Rook condition 2 succes");
				if(canCastle==true) {
					canCastle = false;
				}
				return;
			}
			else {
				System.out.println("Niepoprawny ruch, ruch po skosie : error Rook move cond.1");
				return;
			}
		}
		else {
			System.out.println("Nie podnosisz odpowiedniej figury : error Rook move cond.2");
			return;
		}
	}
	void castleMeShort() {
		if(canCastle==true) {
			posX=posX+3;
		}
		else {
			System.out.println("Error: short castle, A file rook");
		}
	}
	void castleMeLong() {
		if(canCastle==true) {
			posX=posX-2;
		}
		else {
			System.out.println("Error short castle, F file rook");
		}
	}
}


class Queen extends Pieces{
	
	Queen(boolean isWhite){
		this.isWhite = isWhite;
		iAm = 'Q';
	}
	boolean noWay(int x0, int x1, int y0, int y1, Pieces[] box) {
		if(y0==y1) {
			for (int i = x0; i <= x1; i++) {
				for (int j = 0; j < box.length; j++) {
					if(box[j].posX==i) {
						System.out.println("Cos stoi na drodze hetmana! : "+box[j].name + " " + box[j].retColour(box[j].isWhite) + " X axis");
						return true;
					}
				}
			}
		}
		else if(x0==x1) {
			for (int i = y0; i <= y1; i++) {
				for (int j = 0; j < box.length; j++) {
					if(box[j].posY==i) {
						System.out.println("Cos stoi na drodze hetmana! : "+box[j].name + " " + box[j].retColour(box[j].isWhite) + " Y axis");
						return true;
					}
				}
			}
		}
		else if (x0 < x1 && y0 < y1) {
			for (int i = y0; i <= y1; i++) {
				for (int j = x0; j <= x1; j++) {
					for (int j2 = 0; j2 < box.length; j2++) {
						if (box[j2].posY == i && box[j2].posX == j) {
							System.out.println("Cos stoi na drodze hetmana! : " + box[j2].name + " "
									+ box[j2].retColour(box[j2].isWhite) + " skos++");
							return true;
						}
					}
				}
			}
		} else if (x0 > x1 && y0 > y1) {
			for (int i = y1; i <= y0; i++) {
				for (int j = x1; j <= x0; j++) {
					for (int j2 = 0; j2 < box.length; j2++) {
						if (box[j2].posY == i && box[j2].posX == j) {
							System.out.println("Cos stoi na drodze hetmana! : " + box[j2].name + " "
									+ box[j2].retColour(box[j2].isWhite) + " skos--");
							return true;
						}
					}
				}
			}
		} else if (x0 < x1 && y0 > y1) {
			for (int i = y1; i <= y0; i++) {
				for (int j = x0; j <= x1; j++) {
					for (int j2 = 0; j2 < box.length; j2++) {
						if (box[j2].posY == i && box[j2].posX == j) {
							System.out.println("Cos stoi na drodze hetmana! : " + box[j2].name + " "
									+ box[j2].retColour(box[j2].isWhite) + " skos+-");
							return true;
						}
					}
				}
			}
		} else {
			for (int i = y0; i <= y1; i++) {
				for (int j = x1; j <= x0; j++) {
					for (int j2 = 0; j2 < box.length; j2++) {
						if (box[j2].posY == i && box[j2].posX == j) {
							System.out.println("Cos stoi na drodze hetmana! : " + box[j2].name + " "
									+ box[j2].retColour(box[j2].isWhite) + " skos-+");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	void move(int x0, int x1, int y0, int y1) {
		if(posX==x0 || posY==y0) {
			if((x0<x1 & y0<y1) | (x0>x1 & y0>y1)) {
				if(x0+x1==y0+y1) {
					posX=x1;
					posY=y1;
					System.out.println("Queen condition 1 succes");
					return;
				}
				else {
					System.out.println("Wykonano niepoprawny ruch : error Queen move cond.1");
					return;
				}
			}
			else if((x0>x1 & y0<y1) | (x0<x1 & y0>y1)) {
				if(x0+y0==x1+y1) {
					posX=x1;
					posY=y1;
					System.out.println("Queen condition 2 succes");
					return;
				}
				else {
					System.out.println("Wykonano niepoprawny ruch : error Queen move cond.2");
					return;
				}
			}
			if(posX==x0 && x0==x1) {
				posY = y1;
				System.out.println("Queen condition 3 succes");
				
			}
			else if(posY==y0 && y0==y1) {
				posX = x1;
				System.out.println("Queen condition 4 succes");
				return;
			}
			else {
				System.out.println("Niepoprawny ruch, ruch po skosie : error Queen move cond.3");
				return;
			}
		}
		else {
			System.out.println("Nie podnosisz odpowiedniej figury : error Queen move cond.0");
			return;
		}
	}
}

class King extends Pieces{
	
	public boolean isCheck;
	public boolean canCastle;
	
	King(boolean isWhite){
		isCheck = false;
		canCastle = true;
		this.isWhite = isWhite;
		iAm = 'K';
	}
	boolean noWay(int x1, int y1, Pieces[] box) {
		for (int i = 0; i < box.length; i++) {
			if(box[i].posX==x1 && box[i].posY==y1) {
				System.out.println("Cos stoi na miejscu docelowym krola! : "+box[i].name + " " + box[i].retColour(box[i].isWhite));
				return true;
			}
		}
		return false;
	}
	void checkCheck() {
		
	}
	
	void move(int x0, int x1, int y0, int y1) {
		if(posX==x0 || posY==y0) {
			if(x0==x1+1 || x0==x1-1) {
				if(y0==y1+1 || y0==y1-1) {
					posX = x1;
					posY = y1;
					System.out.println("King contition 1 succes");
					if(canCastle==true) {
						canCastle = false;
					}
					return;
				}
				else {
					System.out.println("Niepoprawny ruch w oY : error King move cond.1");
					return;
				}
			}
			else {
				System.out.println("Niepoprawny ruch w oX : error King move cond.2");
				return;
			}
		}
		else {
			System.out.println("Nie podnosisz odpowiedniej figury : error King move cond.3");
			return;
		}
	}
	
	void castleMeShort() {
		if(canCastle==true) {
			posX=posX+2;
			System.out.println("Error: short castle King");
		}
		else {
			
		}
	}
	void castleMeLong() {
		if(canCastle==true) {
			posX=posX-2;
		}
		else {
			System.out.println("Error: long castle King");
		}
	}
}

class isEmpty extends Pieces {
	
}



