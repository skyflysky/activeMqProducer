package sky.tool.activemq.calculation.constant;

/**
 * 航天云网redis中的key的类型
 * @author skygd
 */
public enum HtywType
{
	TENDENCY("趋势分析" , "tendency"),
	
	ORIGINAL("时域波形" , "original"),
	
	FREQUENCY("频谱分析" , "frequency"),
	
	ROTATION("转速数据" , "rotation"),
	
	CEPSTRUM("倒谱分析" , "cepstrum"),
	
	ENVELOPE("包络解调" , "envelope"),
	
	REPROCESS("波形再处理" , "reprocess");
	
	public static HtywType toHtywType(String type)
	{
		switch (type)
		{
			case "tendency":
				return HtywType.TENDENCY;
			case "original":
				return HtywType.ORIGINAL;
			case "frequency":
				return HtywType.FREQUENCY;
			case "rotation":
				return HtywType.ROTATION;
			case "cepstrum":
				return HtywType.CEPSTRUM;
			case "envelope":
				return HtywType.ENVELOPE;
			case "reprocess":
				return HtywType.REPROCESS;
			default:
				throw new RuntimeException("error input:" + type);
		}
	}
	
	private String chineseName;
	
	private String englishName;
	
	private HtywType(String chineseName , String englishName)
	{
		this.chineseName = chineseName;
		this.englishName = englishName;
	}

	public String getChineseName()
	{
		return chineseName;
	}

	public void setChineseName(String chineseName)
	{
		this.chineseName = chineseName;
	}

	public String getEnglishName()
	{
		return englishName;
	}

	public void setEnglishName(String englishName)
	{
		this.englishName = englishName;
	}
}
