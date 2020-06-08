package test;

/**
 * 内存对象 包含型号和大小
 * @author 81895
 *
 */
public class Memory {

	private String model;
	private String size;
	
	
	
	public Memory() {
	}
	public Memory(String model, String size) {
		this.model = model;
		this.size = size;
	}
	@Override
	public String toString() {
		return "Memory [model=" + model + ", size=" + size + "]";
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
	
}
