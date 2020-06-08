package test;

/**
 * 计算机对象
 * @author 81895
 *
 */
public class Computer {
	private String name;//电脑品牌名
	private String model;//型号
	private String system;//系统
	CPU cpu = new CPU();//处理器
	Memory memory = new Memory();//内存
	
	
	public Computer() {
	}
	public Computer(String name, String model, String system, CPU cpu, Memory memory) {
		this.name = name;
		this.model = model;
		this.system = system;
		this.cpu = cpu;
		this.memory = memory;
	}
	@Override
	public String toString() {
		return "Computer [name=" + name + ", model=" + model + ", system=" + system + ", cpu=" + cpu + ", memory="
				+ memory + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public CPU getCpu() {
		return cpu;
	}
	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}
	public Memory getMemory() {
		return memory;
	}
	public void setMemory(Memory memory) {
		this.memory = memory;
	}
	
	
	
	
	
	

	
	
}
