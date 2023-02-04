package ElementsPages;

import java.util.List;

public class Element {
	public String id;
	public String description;
	public List<Locator> locators;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Locator> getList() {
		return locators;
	}
	public void setList(List<Locator> locators) {
		this.locators = locators;
	}
	

}
