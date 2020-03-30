import java.util.HashMap;
import java.util.Map;

public final class Immutable {
	private final int empId;
	private final String employeeName;
	private final HashMap<String, Integer> subjectMarks;

	public Immutable(int empId, String employeeName, HashMap<String, Integer> subjectMarks) {
		super();
		this.empId = empId;
		this.employeeName = employeeName;
		this.subjectMarks = new HashMap<String, Integer>();
		for (Map.Entry<String, Integer> e : subjectMarks.entrySet()) {
			this.subjectMarks.put(e.getKey(), e.getValue());
		}
	}

	public int getEmpId() {
		return empId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> getSubjectMarks() {
		return (HashMap<String, Integer>) subjectMarks.clone();
	}

}
