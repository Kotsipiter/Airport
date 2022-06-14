package FlightManager;

import java.util.ArrayList;

public class Menu {
	private String menuCode;
	private ArrayList<String> mainDishes;
	private ArrayList<String> desserts;
	private ArrayList<String> drinks;

	public Menu(String menuCode, ArrayList<String> mainDishes, ArrayList<String> desserts, ArrayList<String> drinks) {
		super();
		this.menuCode = menuCode;
		this.mainDishes = mainDishes;
		this.desserts = desserts;
		this.drinks = drinks;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public ArrayList<String> getMainDishes() {
		return mainDishes;
	}

	public void setMainDishes(ArrayList<String> mainDishes) {
		this.mainDishes = mainDishes;
	}

	public ArrayList<String> getDesserts() {
		return desserts;
	}

	public void setDesserts(ArrayList<String> desserts) {
		this.desserts = desserts;
	}

	public ArrayList<String> getDrinks() {
		return drinks;
	}

	public void setDrinks(ArrayList<String> drinks) {
		this.drinks = drinks;
	}

	@Override
	public String toString() {
		return " mainDishes=" + mainDishes + ", desserts=" + desserts + ", drinks=" + drinks + "]";
	}

}
