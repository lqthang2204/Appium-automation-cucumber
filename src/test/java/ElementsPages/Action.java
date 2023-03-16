package ElementsPages;

import java.util.List;

public class Action {
	public String id;
	public String description;
	public List<ActionElements> actionElements;
	public String condition;
	public String timeout;

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
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
	public List<ActionElements> getActionElements() {
		return actionElements;
	}
	public void setActionElements(List<ActionElements> actionElements) {
		this.actionElements = actionElements;
	}
	

}
