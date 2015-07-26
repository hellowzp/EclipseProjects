package ml.decisiontree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 * http://blog.csdn.net/zhyoulun/article/details/41978381
 * http://blog.csdn.net/zhyoulun/article/details/42268413
 * 
 *《 数据挖掘概念与技术（第3版）》214页-216页
 * 
 * @author Benchun
 *
 */
public class DecisionTree {
	private ArrayList<ArrayList<String>> allDatas;
	private ArrayList<String> allAttributes;

	/**
	 * 从文件中读取所有相关数据
	 * 
	 * @param dataFilePath
	 * @param attrFilePath
	 */
	public DecisionTree( String dataFilePath, String attrFilePath) {
		try {
			this.allDatas = new ArrayList<>();
			this.allAttributes = new ArrayList<>();

			InputStreamReader inputStreamReader = new InputStreamReader(
					new FileInputStream(new File(dataFilePath)));
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String[] strings = line.split(",");
				ArrayList<String> data = new ArrayList<>();
				for (int i = 0; i < strings.length; i++)
					data.add(strings[i]);
				this.allDatas.add(data);
			}

			inputStreamReader = new InputStreamReader(new FileInputStream(
					new File(attrFilePath)));
			bufferedReader = new BufferedReader(inputStreamReader);
			while ((line = bufferedReader.readLine()) != null) {
				String[] strings = line.split(",");
				for (int i = 0; i < strings.length; i++)
					this.allAttributes.add(strings[i]);
			}

			inputStreamReader.close();
			bufferedReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param allDatas
	 * @param allAttributes
	 */
	public DecisionTree( ArrayList<ArrayList<String>> allDatas,
			ArrayList<String> allAttributes) {
		this.allDatas = allDatas;
		this.allAttributes = allAttributes;
	}

	public ArrayList<ArrayList<String>> getAllDatas() {
		return allDatas;
	}

	public void setAllDatas(ArrayList<ArrayList<String>> allDatas) {
		this.allDatas = allDatas;
	}

	public ArrayList<String> getAllAttributes() {
		return allAttributes;
	}

	public void setAllAttributes(ArrayList<String> allAttributes) {
		this.allAttributes = allAttributes;
	}

	/**
	 * 递归生成决策数
	 * 
	 * @return
	 */
	public static TreeNode generateDecisionTree(
			ArrayList<ArrayList<String>> datas, ArrayList<String> attrs) {
		TreeNode treeNode = new TreeNode();

		// 如果D中的元素都在同一类C中，then return this node
		if (isInTheSameClass(datas)) {
			treeNode.setName(datas.get(0).get(datas.get(0).size() - 1));
			return treeNode;
		}
		// 如果attrs为空，then(这种情况一般不会出现，我们应该是要对所有的候选属性集合构建决策树)
		if (attrs.size() == 0)
			return treeNode;

		CriterionID3 criterionID3 = new CriterionID3(datas, attrs);
		int splitingCriterionIndex = criterionID3.attributeSelectionMethod();

		treeNode.setName(attrs.get(splitingCriterionIndex));
		treeNode.setRules(getValueSet(datas, splitingCriterionIndex));

		attrs.remove(splitingCriterionIndex);

		Map<String, ArrayList<ArrayList<String>>> subDatasMap = criterionID3
				.getSubDatasMap(splitingCriterionIndex);

		for (String key : subDatasMap.keySet()) {
			ArrayList<TreeNode> treeNodes = treeNode.getChildren();
			treeNodes.add(generateDecisionTree(subDatasMap.get(key), attrs));
			treeNode.setChildren(treeNodes);
		}

		return treeNode;
	}

	/**
	 * 获取datas中index列的值域
	 * 
	 * @param data
	 * @param index
	 * @return
	 */
	public static ArrayList<String> getValueSet(
			ArrayList<ArrayList<String>> datas, int index) {
		ArrayList<String> values = new ArrayList<String>();
		String r = "";
		for (int i = 0; i < datas.size(); i++) {
			r = datas.get(i).get(index);
			if (!values.contains(r)) {
				values.add(r);
			}
		}
		return values;
	}

	/**
	 * 最后一列是类标号，判断最后一列是否相同
	 * 
	 * @param datas
	 * @return
	 */
	private static boolean isInTheSameClass(ArrayList<ArrayList<String>> datas) {
		String flag = datas.get(0).get(datas.get(0).size() - 1);// 第0行，最后一列赋初值
		for (int i = 0; i < datas.size(); i++) {
			if (!datas.get(i).get(datas.get(i).size() - 1).equals(flag))
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String dataPath = "res/data.csv";
		String attrPath = "res/attr.csv";

		// 初始化原始数据
		DecisionTree decisionTree = new DecisionTree(dataPath, attrPath);

		// 生成决策树
		TreeNode treeNode = generateDecisionTree(decisionTree.getAllDatas(),
				decisionTree.getAllAttributes());

		print(treeNode, 0);
	}

	private static void print(TreeNode treeNode, int level) {
		for (int i = 0; i < level; i++)
			System.out.print("\t");
		System.out.print(treeNode.getName());
		System.out.print("(");
		for (int i = 0; i < treeNode.getRules().size(); i++)
			System.out.print((i + 1) + ":" + treeNode.getRules().get(i) + "; ");
		System.out.println(")");

		ArrayList<TreeNode> treeNodes = treeNode.getChildren();
		for (int i = 0; i < treeNodes.size(); i++) {
			print(treeNodes.get(i), level + 1);
		}
	}

}
