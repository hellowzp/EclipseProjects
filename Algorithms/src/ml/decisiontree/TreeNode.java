package ml.decisiontree;

import java.util.ArrayList;

public class TreeNode {
	
	private String name; // 该结点的名称（分裂属性）
	private ArrayList<String> rules; // 结点的分裂规则(假设均为离散值)
	private ArrayList<TreeNode> children; // 子结点

	public TreeNode() {
		this.name = "";
		this.rules = new ArrayList<String>();
		this.children = new ArrayList<TreeNode>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getRules() {
		return rules;
	}

	public void setRules(ArrayList<String> rules) {
		this.rules = rules;
	}

	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}

}
