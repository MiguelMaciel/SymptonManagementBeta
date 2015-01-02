package miguelmaciel.capstone.adapters;

public class AlarmTimes {
	private String _id;
	private String AlarmTime;

	public AlarmTimes(String _id, String alarmTime) {
		super();
		this._id = _id;
		AlarmTime = alarmTime;
	}

	public AlarmTimes(String alarmTime) {
		super();
		AlarmTime = alarmTime;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getAlarmTime() {
		return AlarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		AlarmTime = alarmTime;
	}

}
