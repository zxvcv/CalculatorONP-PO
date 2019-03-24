//import Kalkulator.ONP.PRIORITY;

public class Calculator {
	
	private static class ONP {
		
		private enum PRIORITY{
			VARIABLE,
			CONST_VARIABLE,
			DOT_COMMA,
			PRIORITY_0,
			PRIORITY_1,
			PRIORITY_2,
			PRIORITY_3
		}
		
		private static boolean compare_priority(PRIORITY pr1, PRIORITY pr2)
		{
			boolean comp_result;
			
			switch (pr1) 
			{
			case PRIORITY_0:
				comp_result = false;
				break;
			case PRIORITY_1:
				switch (pr2) 
				{
				case PRIORITY_0:
					comp_result = true;
					break;
				default:
					comp_result = false;
					break;
				}
				break;
			case PRIORITY_2:
				switch (pr2) 
				{
				case PRIORITY_0:
				case PRIORITY_1:
					comp_result = true;
					break;
				default:
					comp_result = false;
					break;
				}
				break;
			case PRIORITY_3:
				switch (pr2) 
				{
				case PRIORITY_0:
				case PRIORITY_1:
				case PRIORITY_2:
					comp_result = true;
					break;
				default:
					comp_result = false;
					break;
				}
				break;

			default:
				comp_result = true;
				break;
			}
			return comp_result;
		}
		
		private static PRIORITY getPriority(String ch)
		{
			PRIORITY ch_priority;
			switch (ch) 
			{
				case "(":
					ch_priority = PRIORITY.PRIORITY_0;
					break;
				case "+": case "-": case ")":
					ch_priority = PRIORITY.PRIORITY_1;
					break;
				case "*": case "/": case "%": case "~":
					ch_priority = PRIORITY.PRIORITY_2;
					break;
				case "^":
					ch_priority = PRIORITY.PRIORITY_3;
					break;
				case "0": case "1": case "2": case "3": case "4":
				case "5": case "6": case "7": case "8": case "9":
					ch_priority = PRIORITY.CONST_VARIABLE;
					break;
				case ".": case ",":
					ch_priority = PRIORITY.DOT_COMMA;
					break;
				default:
					ch_priority = PRIORITY.VARIABLE;
					break;
			}
			return ch_priority;
		}
		
		public static String translate(String quation)
		{
			String out = new String();
			Stack stack = new Stack();
			
			while(!quation.isEmpty())
			{
				String ch = quation.substring(0, 1);
				if(quation.length() > 1)
					quation = quation.substring(1, quation.length());
				else
					quation = "";
				PRIORITY ch_priority = getPriority(ch);
				
				switch (ch_priority)
				{
					case CONST_VARIABLE:
					case DOT_COMMA:
						String str2 = ch;
						if(!quation.isEmpty())
						{
							ch = quation.substring(0, 1);
							while(getPriority(ch) == PRIORITY.CONST_VARIABLE || getPriority(ch) == PRIORITY.DOT_COMMA)
							{
								if(quation.length() > 1)
									quation = quation.substring(1, quation.length());
								else
									quation = "";
								str2 = str2 + ch;
								if(quation.isEmpty())
									break;
								ch = quation.substring(0, 1);
							}
						}
						out = out + "[" + str2 + "]";
						break;
					case VARIABLE:
						out = out + ch;
						break;
					case PRIORITY_0: // znak "("
						stack.push(ch);
						ch = quation.substring(0, 1);
						if(ch.equals("-"))
						{
							stack.push("~");
							quation = quation.substring(1, quation.length());
						}
						break;
					case PRIORITY_1:
					case PRIORITY_2:	
					case PRIORITY_3:
						if(ch.equals(")"))
						{
							while(!stack.front().equals("("))
							{	
								out = out + stack.front();
								stack.pop();
							}
							stack.pop();
							break;
						}
						if(out.isEmpty() && ch.equals("-"))
						{
							stack.push("~");
							break;
						}
						while(!stack.isEmpty())
						{
							if(compare_priority(ch_priority, getPriority(stack.front())))
							{
								stack.push(ch);
								break;
							}
							else 
							{
								out = out + stack.front();
								stack.pop();
							}
						}
						if(stack.isEmpty())
								stack.push(ch);
						break;
					default:
						break;
				}
			}
			
			while(!stack.isEmpty())
			{
				out = out + stack.front();
				stack.pop();
			}
			return out;
		}
	}
	
	public static String ONP_translate(String quation)
	{
		if(!quation.substring(quation.length()-1, quation.length()).equals("="))
			return new String("error, it's not a quation");
		quation = quation.substring(0, quation.length()-1);
		
		return ONP.translate(quation) + "=";
	}
	
	public static String calculate(String quation)
	{
		if(!quation.substring(quation.length()-1, quation.length()).equals("="))
			return new String("error, it's not a quation");
		//quation = quation.substring(0, quation.length()-1);
		
		Stack stack = new Stack();
		float out = 0;
		float help_val = 0;
		String help_str = new String();
		
		String ch = quation.substring(0, 1);
		quation = quation.substring(1, quation.length());
		
		
		while(!ch.equals("="))
		{
			help_str = "";
			switch (ch)
			{
			case "[":
				ch = quation.substring(0, 1);
				quation = quation.substring(1, quation.length());
				do
				{
					help_str = help_str + ch;
					ch = quation.substring(0, 1);
					quation = quation.substring(1, quation.length());
				}while(!ch.equals("]"));
				stack.push(help_str);
				break;
			case "+":
				help_val = Float.valueOf(stack.front());
				stack.pop();
				out = Float.valueOf(stack.front()) + help_val;
				stack.pop();
				stack.push(""+out);
				break;
			case "-":
				help_val = Float.valueOf(stack.front());
				stack.pop();
				out = Float.valueOf(stack.front()) - help_val;
				stack.pop();
				stack.push("" + out);
				break;
			case "/":
				help_val = Float.valueOf(stack.front());
				stack.pop();
				if(help_val == 0)
					return new String("err, divide by 0");
				out = Float.valueOf(stack.front()) / help_val;
				stack.pop();
				stack.push("" + out);
				break;
			case "*":
				help_val = Float.valueOf(stack.front());
				stack.pop();
				out = Float.valueOf(stack.front()) * help_val;
				stack.pop();
				stack.push("" + out);
				break;
			case "%":
				help_val = Float.valueOf(stack.front());
				stack.pop();
				out = Float.valueOf(stack.front()) % help_val;
				stack.pop();
				stack.push("" + out);
				break;
			case "^":
				help_val = Float.valueOf(stack.front());
				stack.pop();
				out = (float)Math.pow(Float.valueOf(stack.front()), help_val);
				stack.pop();
				stack.push("" + out);
				break;
			case "~":
				out = -Float.valueOf(stack.front());
				stack.pop();
				stack.push("" + out);
				break;
			default:
				return new String("err, unknown operator");
			}
			
			ch = quation.substring(0, 1);
			if(quation.length() > 1)
				quation = quation.substring(1, quation.length());
			else
				quation = "";
		}
		
		
		return new String(stack.front());
	}
	
}
