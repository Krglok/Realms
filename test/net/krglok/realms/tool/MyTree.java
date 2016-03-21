package net.krglok.realms.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * simple tree class for build recursively trees with Strings
 * a data object can be associate with the treenode
 * 
 *  multiple root nodes are possible
 * 
 * @author Krglok
 *
 */
public class MyTree
{
	private ArrayList<Node<String>> roots;		// nodeTree
	private HashMap<String,Object> dataList;	// DataObject realated to nodes

	public MyTree()
	{
		roots = new ArrayList<Node<String>>();
		dataList = new HashMap<String,Object>();
	}

	/**
	 * Basic class for the tree 
	 * 
	 * @author Krglok
	 *
	 * @param <T>
	 */
	public static class Node<T>
	{
		private T key;
		public T getKey()
		{
			return key;
		}

		public void setKey(T key)
		{
			this.key = key;
		}

		public List<Node<T>> getChildren()
		{
			return children;
		}

		public void setChildren(List<Node<T>> children)
		{
			this.children = children;
		}

		private Node<T> parent;
		private List<Node<T>> children;
		
		/**
		 * super constructor, all values are null !
		 */
		public Node()
		{
			super();
			this.key = null;
			this.setParent(null);
			this.children = null;
		}

		/**
		 * constructor with given key
		 * parent is null
		 * 
		 * @param key
		 */
		public Node(T key)
		{
			this.key = key;
			this.parent = null;
			this.children = new ArrayList<Node<T>>();
		}
		
		/**
		 * add a node to the child list
		 * 
		 * @param parent
		 * @param child
		 * @return
		 */
		public Node<T> addChild( Node<T> child)
		{
			child.parent = this;
			this.children.add(child);
			return child;
		}
		
		/**
		 * get a child by value
		 * 
		 * @param parent
		 * @param key
		 * @return
		 */
		public Node<T> getChild( T key)
		{
			for (Node<T> node : this.children)
			{
				if (node.key.equals(key))
				{
					return node;
				}
			}
			return null;
		}
		
		/**
		 * remove a node from child list 
		 * 
		 * @param parent
		 * @param key to be removable
		 * @return
		 */
		public boolean removeChild( T key)
		{
			for (Node<T> node : this.children)
			{
				if (node.key.equals(key))
				{
					this.children.remove(node);
					return true;
				}
			}
			return false;
		}

		/**
		 * @return the parent
		 */
		public Node<T> getParent()
		{
			return this.parent;
		}

		/**
		 * @param parent the parent to set
		 */
		public void setParent(Node<T> parent)
		{
			this.parent = parent;
		}

	}

	/**
	 * add a data object to a given node
	 * 
	 * @param node
	 * @param data
	 */
	public boolean addData(Node<String> node, Object data)
	{
		if ((node.key != null) && (data != null))
		{
			dataList.put(node.key, data);
			return true;
		} else
		{
			return false;
		}
	}
	
	/**
	 * get data object for a given node
	 * 
	 * @param node
	 * @return
	 */
	public Object getData(Node<String> node)
	{
		if (dataList.containsKey(node.key) == true)
		{
			return dataList.get(node.key);
		}
		return null;
	}
	
	/**
	 * add a child node and data object to the parent node
	 * 
	 * @param parent
	 * @param child
	 * @param data
	 * @return
	 */
	public Node<String> addNode(Node<String> parent, Node<String> child, Object data)
	{
		if (child != null)
		{
			parent.addChild(child);
			addData(child, data);
		}
		return child;
	}

	public ArrayList<Node<String>> getRoots()
	{
		return roots;
	}

	public HashMap<String, Object> getDataList()
	{
		return dataList;
	}
}
