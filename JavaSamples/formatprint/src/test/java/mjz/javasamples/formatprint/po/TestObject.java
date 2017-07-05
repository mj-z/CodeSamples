package mjz.javasamples.formatprint.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestObject {
	private int id;
	
	private String label;
	
	private String desc;
	
	private Long longId;
	
	private TestInnerObject inner1;
	
	private BigDecimal amount;
	
	private LocalDateTime createtime;
	
	private boolean isTested;
	
	private TestInnerObject inner2;

	public TestInnerObject getInner1() {
		return inner1;
	}

	public void setInner1(TestInnerObject inner1) {
		this.inner1 = inner1;
	}

	public TestInnerObject getInner2() {
		return inner2;
	}

	public void setInner2(TestInnerObject inner2) {
		this.inner2 = inner2;
	}

	public boolean isTested() {
		return isTested;
	}

	public void setTested(boolean isTested) {
		this.isTested = isTested;
	}

	public int getId() {
		return id;
	}

	public Long getLongId() {
		return longId;
	}

	public void setLongId(Long longId) {
		this.longId = longId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public LocalDateTime getCreatetime() {
		return createtime;
	}

	public void setCreatetime(LocalDateTime createtime) {
		this.createtime = createtime;
	}
	
	
}
