package com.xiji.cashloan.core.common.util.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author wnb
 * @date 2018/11/27
 * @version 1.0.0
 */
public class FormulaProcessor {
	private static FormulaProcessor self = null;

	private Pattern pattern = Pattern.compile("\\$(\\d+)");

	private FormulaProcessor() {

	}

	public static FormulaProcessor getInstance() {
		if (self == null)
			self = new FormulaProcessor();
		return self;
	}

	public void fillValue(TableDataRow row) {
		/*
		 * HashSet<Integer> computed = new HashSet<Integer>(); for (TableDataCell cell:row.getCells()){ TableColumn thc
		 * = row.getTable().getTableHeader().getColumnAt(cell.getColumnIndex()); int type = thc.getColumnType(); if
		 * (type == TableColumn.COLUMN_TYPE_FORMULA && thc.getAggregateRule() != null){ String f =
		 * convertFormula(thc.getAggregateRule()); try{ Interpreter process = new Interpreter();
		 * process.getNameSpace().importCommands("com..kaas.web.report.bean" ); process.set("row", row);
		 * process.set("computed", computed); Object ret = process.eval(f); double v = (Double)ret; cell.setValue(v);
		 * computed.add(cell.getColumnIndex()); } catch (Exception e){ e.printStackTrace(); cell.setValue(0);
		 * cell.setValue("-"); computed.add(cell.getColumnIndex()); } } }
		 */
	}

	@SuppressWarnings("unused")
	private String convertFormula(String formula) {
		Matcher m = pattern.matcher(formula);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "getValue(row, " + m.group(1) + ")");
		}
		m.appendTail(sb);

		return sb.toString();
	}
}
