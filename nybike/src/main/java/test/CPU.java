package test;

/**
 * CPU对象包含型号 核心数 频率
 * @author 81895
 *
 */
public class CPU {
  private String model;
  private Integer core;
  private String  frequency;
  
  
  
public CPU(String model, Integer core, String frequency) {
	this.model = model;
	this.core = core;
	this.frequency = frequency;
}
@Override
public String toString() {
	return "CPU [model=" + model + ", core=" + core + ", frequency=" + frequency + "]";
}
public CPU() {
}
public String getModel() {
	return model;
}
public void setModel(String model) {
	this.model = model;
}
public Integer getCore() {
	return core;
}
public void setCore(Integer core) {
	this.core = core;
}
public String getFrequency() {
	return frequency;
}
public void setFrequency(String frequency) {
	this.frequency = frequency;
}
  
  
}
