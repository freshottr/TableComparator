import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 
 */

/**
 * @author Nikola Milosevic
 *
 */
public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String GoldStandardDir = args[0];
		String OutputDir = args[1];
		String ResultCSV = args[2];
		System.out.println( GoldStandardDir);
		File GoldStandarD = new File(GoldStandardDir);
		File[] GoldStandardFiles = GoldStandarD.listFiles();
		File Outputs = new File(OutputDir);
		File[] OutputFiles = Outputs.listFiles();
		File file = new File(ResultCSV);
		try{
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		String TableResultLine = "TableName,Structural Type,StructuralTypeCorrect,Cell roles - Header TP,Cell roles - Header FP,Cell roles - Header FN,Cell roles - Stub TP,Cell roles - Stub FP,Cell roles - Stub FN,Cell roles - SuperRow TP,Cell roles - SuperRow FP,Cell roles - SuperRow FN,Cell roles - Data TP,Cell roles - Data FP,Cell roles - Data FN, Cell references - Header TP,Cell references - Header FP,Cell references - Header FN,Cell references - Stub TP,Cell references - Stub FP,Cell references - Stub FN,Cell references - SuperRow TP,Cell references - SuperRow FP,Cell references - SuperRow FN";
		bw.write(TableResultLine+"\n");
		bw.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		for(int i = 0;i<OutputFiles.length;i++){

			System.out.println(GoldStandardFiles[i].getName());
			System.out.println(OutputFiles[i].getName());
			LinkedList<Table> GoldTables = ReadFile(GoldStandardFiles[i]);
			LinkedList<Table> OutputTables = ReadFile(OutputFiles[i]);
			CompareTables(ResultCSV,GoldTables,OutputTables);
			
		}
		
	}
	
	public static void CompareTables(String resultCSVFile,LinkedList<Table> GoldTables,LinkedList<Table> OutputTables)
	{
		String TableResultLine = "";
		int numTablesEverythongCorrect=0;
		int StructTypeCorrect = 0;
		int StructTypeAll = 0;
		int CellRoleHeaderTP = 0;
		int CellRoleHeaderFP = 0;
		int CellRoleHeaderFN = 0;
		int CellRoleStubTP = 0;
		int CellRoleStubFP = 0;
		int CellRoleStubFN  = 0;
		int CellRoleSuperRowTP = 0;
		int CellRoleSuperRowFP = 0;
		int CellRoleSuperRowFN = 0; 
		int CellRoleDataTP = 0;
		int CellRoleDataFP = 0;
		int CellRoleDataFN = 0;
		
		int HeaderRefTP = 0;
		int HeaderRefFP = 0;
		int HeaderRefFN = 0;
		int StubRefTP = 0;
		int StubRefFP = 0;
		int StubRefFN = 0;
		int SuperRowRefTP=0;
		int SuperRowRefFP = 0;
		int SuperRowRefFN = 0;
		
		File file = new File(resultCSVFile);
		try{
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);

		
		for(int i = 0;i<GoldTables.size();i++)
		{
			TableResultLine = "";
			numTablesEverythongCorrect=0;
			StructTypeCorrect = 0;
			StructTypeAll = 0;
			CellRoleHeaderTP = 0;
			CellRoleHeaderFP = 0;
			CellRoleHeaderFN = 0; 
			CellRoleStubTP = 0;
			CellRoleStubFP = 0;
			CellRoleStubFN  = 0;
			CellRoleSuperRowTP = 0;
			CellRoleSuperRowFP = 0;
			CellRoleSuperRowFN = 0; 
			CellRoleDataTP = 0;
			CellRoleDataFP = 0;
			CellRoleDataFN = 0;
			
			HeaderRefTP = 0;
			HeaderRefFP = 0;
			HeaderRefFN = 0;
			StubRefTP = 0;
			StubRefFP = 0;
			StubRefFN = 0;
			SuperRowRefTP=0;
			SuperRowRefFP = 0;
			SuperRowRefFN = 0;
			Table GoldTable = GoldTables.get(i);
			Table OutputTable = OutputTables.get(i);
			if(GoldTable.getStructureType().equals(OutputTable.getStructureType()))
			{
				StructTypeCorrect++;
				StructTypeAll++;
			}
			else
			{
				StructTypeAll++;
			}
			
			for(int j=0;j<GoldTable.cells.size();j++)
			{
				Cell GoldCell = GoldTable.cells.get(j);
				Cell OutputCell = OutputTable.cells.get(j); 
				
				//HeaderRef--------------------------------------------------
				if(GoldCell.getHeaderRef()!=null)
				{
					if(OutputCell.getHeaderRef()!=null)
					{
						if(OutputCell.getHeaderRef().equals(GoldCell.getHeaderRef()))
						{
							HeaderRefTP++;
						}
						
						if(!OutputCell.getHeaderRef().equals(GoldCell.getHeaderRef()))
						{
							HeaderRefFP++;
						}
					}
					else
					{
						HeaderRefFN++;
					}
				}
				
				//StubRef--------------------------------------------------
				if(GoldCell.getStubRef()!=null)
				{
					if(OutputCell.getStubRef()!=null)
					{
						if(OutputCell.getStubRef().equals(GoldCell.getStubRef()))
						{
							StubRefTP++;
						}
						
						if(!OutputCell.getStubRef().equals(GoldCell.getStubRef()))
						{
							StubRefFP++;
						}
					}
					else
					{
						StubRefFN++;
					}
				}
				
				//SuperRowRef--------------------------------------------------
				if(GoldCell.getSuperRowRef()!=null)
				{
					if(OutputCell.getSuperRowRef()!=null)
					{
						if(OutputCell.getSuperRowRef().equals(GoldCell.getSuperRowRef()))
						{
							SuperRowRefTP++;
						}
						
						if(!OutputCell.getSuperRowRef().equals(GoldCell.getSuperRowRef()))
						{
							SuperRowRefFP++;
						}
					}
					else
					{
						SuperRowRefFN++;
					}
				}
				
				
				//Header Cell role ---------------------------------------------------------------------------------
				for(int k=0;k<GoldCell.getCellRole().size();k++)
				{
					if(GoldCell.getCellRole().get(k).equals("Header"))
					{
						boolean isheader =false;
						for(int l=0;l<OutputCell.getCellRole().size();l++)
						{
							if(OutputCell.getCellRole().get(l).equals("Header"))
							{
								isheader = true;
								CellRoleHeaderTP++;
							}
						}
						if(isheader ==false)
						{
							CellRoleHeaderFN++;
						}
					}
				}
				
				boolean noGoldenHeader = true;
				for(int k=0;k<GoldCell.getCellRole().size();k++)
				{
					if(GoldCell.getCellRole().get(k).equals("Header"))
					{
						noGoldenHeader = false;
					}
				}
				if(noGoldenHeader)
				{
					for(int k=0;k<OutputCell.getCellRole().size();k++)
					{
						if(OutputCell.getCellRole().get(k).equals("Header"))
						{
							CellRoleHeaderFP++;
						}
					}
				}
				
				//Stub Cell role ---------------------------------------------------------------------------------
				for(int k=0;k<GoldCell.getCellRole().size();k++)
				{
					if(GoldCell.getCellRole().get(k).equals("Stub"))
					{
						boolean isstub =false;
						for(int l=0;l<OutputCell.getCellRole().size();l++)
						{
							if(OutputCell.getCellRole().get(l).equals("Stub"))
							{
								isstub = true;
								CellRoleStubTP++;
							}
						}
						if(isstub ==false)
						{
							CellRoleStubFN++;
						}
					}
				}
				
				boolean noGoldenStub = true;
				for(int k=0;k<GoldCell.getCellRole().size();k++)
				{
					if(GoldCell.getCellRole().get(k).equals("Stub"))
					{
						noGoldenStub = false;
					}
				}
				if(noGoldenStub)
				{
					for(int k=0;k<OutputCell.getCellRole().size();k++)
					{
						if(OutputCell.getCellRole().get(k).equals("Stub"))
						{
							CellRoleStubFP++;
						}
					}
				}
				
				//SuperRow Cell role ---------------------------------------------------------------------------------
				for(int k=0;k<GoldCell.getCellRole().size();k++)
				{
					if(GoldCell.getCellRole().get(k).equals("SuperRow"))
					{
						boolean issuperrow =false;
						for(int l=0;l<OutputCell.getCellRole().size();l++)
						{
							if(OutputCell.getCellRole().get(l).equals("SuperRow"))
							{
								issuperrow = true;
								CellRoleSuperRowTP++;
							}
						}
						if(issuperrow ==false)
						{
							CellRoleSuperRowFN++;
						}
					}
				}
				
				boolean noGoldenSuperRow = true;
				for(int k=0;k<GoldCell.getCellRole().size();k++)
				{
					if(GoldCell.getCellRole().get(k).equals("SuperRow"))
					{
						noGoldenSuperRow = false;
					}
				}
				if(noGoldenSuperRow)
				{
					for(int k=0;k<OutputCell.getCellRole().size();k++)
					{
						if(OutputCell.getCellRole().get(k).equals("SuperRow"))
						{
							CellRoleSuperRowFP++;
						}
					}
				}
				
				//Data Cell role ---------------------------------------------------------------------------------
				for(int k=0;k<GoldCell.getCellRole().size();k++)
				{
					if(GoldCell.getCellRole().get(k).equals("Data"))
					{
						boolean isdata =false;
						for(int l=0;l<OutputCell.getCellRole().size();l++)
						{
							if(OutputCell.getCellRole().get(l).equals("Data"))
							{
								isdata = true;
								CellRoleDataTP++;
							}
						}
						if(isdata ==false)
						{
							CellRoleDataFN++;
						}
					}
				}
				
				boolean noGoldenData = true;
				for(int k=0;k<GoldCell.getCellRole().size();k++)
				{
					if(GoldCell.getCellRole().get(k).equals("Data"))
					{
						noGoldenData = false;
					}
				}
				if(noGoldenData)
				{
					for(int k=0;k<OutputCell.getCellRole().size();k++)
					{
						if(OutputCell.getCellRole().get(k).equals("Data"))
						{
							CellRoleDataFP++;
						}
					}
				}
				

			}
			TableResultLine = OutputTable.getFileName()+OutputTable.getTableOrder()+","+GoldTable.getStructureType()+","+StructTypeCorrect+","+CellRoleHeaderTP+","+CellRoleHeaderFP+","+CellRoleHeaderFN+","+CellRoleStubTP+","+CellRoleStubFP+","+CellRoleStubFN+","+CellRoleSuperRowTP+","+CellRoleSuperRowFP+","+CellRoleSuperRowFN+","+CellRoleDataTP+","+CellRoleDataFP+","+CellRoleDataFN+","+HeaderRefTP+","+HeaderRefFP+","+HeaderRefFN+","+StubRefTP+","+StubRefFP+","+StubRefFN+","+SuperRowRefTP+","+SuperRowRefFP+","+SuperRowRefFN;
			bw.write(TableResultLine+"\n");
			
			
		}
		//System.out.println("Correct structural type:"+StructTypeCorrect);
		bw.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static LinkedList<Table> ReadFile(File FileName)
	{
		LinkedList<Table> tablesList = new LinkedList<Table>();
		try{
		BufferedReader reader = new BufferedReader(new FileReader(FileName));
		String line = null;
		String xml = "";
		while ((line = reader.readLine()) != null) {
		    xml +=line+'\n';
		}		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		factory.setNamespaceAware(true);
		factory.setValidating(false);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    Document parse =  builder.parse(is);
	    NodeList tables = parse.getElementsByTagName("Table");		    
	    System.out.println(tables.getLength());
	    for(int j = 0;j<tables.getLength();j++)
	    {
	    	Table tmp_table = new Table();
	    	tmp_table.setFileName(FileName.getName());
	    	List<Node> TableOrder = getChildrenByTagName(tables.item(j),"TableOrder");
	    	tmp_table.setTableOrder(TableOrder.get(0).getTextContent());
	    	List<Node> TableCaption = getChildrenByTagName(tables.item(j),"TableCaption");
	    	tmp_table.setCaption(TableCaption.get(0).getTextContent());
	    	List<Node> TableFooter = getChildrenByTagName(tables.item(j),"TableFooter");
	    	tmp_table.setFooter(TableFooter.get(0).getTextContent());
	    	List<Node> TableStructureType = getChildrenByTagName(tables.item(j),"TableStructureType");
	    	tmp_table.setStructureType(TableStructureType.get(0).getTextContent());
	    	List<Node> TablePragmaticClass = getChildrenByTagName(tables.item(j),"TablePragmaticClass");
	    	tmp_table.setPragmaticType(TablePragmaticClass.get(0).getTextContent());
	    	System.out.println(tmp_table.getStructureType());
	    	tablesList.add(tmp_table);
	    	List<Node> cells = getChildrenByTagName(tables.item(j),"Cells");
	    	for(int k=0;k<cells.size();k++)
	    	{
	    		List<Node> cell = getChildrenByTagName(cells.get(k),"Cell");
	    		for(int l=0;l<cell.size();l++)
		    	{
	    			Cell cellItem = new Cell();
	    			List<Node> CellID = getChildrenByTagName(cell.get(l),"CellID");
	    			cellItem.setCellID(CellID.get(0).getTextContent());
	    			List<Node> CellValue = getChildrenByTagName(cell.get(l),"CellValue");
	    			cellItem.setCellValue(CellValue.get(0).getTextContent());
	    			List<Node> CellType = getChildrenByTagName(cell.get(l),"CellType");
	    			if(CellType!=null&&CellType.size()>0){
	    				cellItem.setCellType(CellType.get(0).getTextContent());
	    			}
	    			List<Node> HeaderRef = getChildrenByTagName(cell.get(l),"HeaderRef");
	    			if(HeaderRef!=null&&HeaderRef.size()>0){
	    				cellItem.setHeaderRef(HeaderRef.get(0).getTextContent());
	    			}
	    			List<Node> StubRef = getChildrenByTagName(cell.get(l),"StubRef");
	    			if(StubRef!=null&&StubRef.size()>0){
	    				cellItem.setStubRef(StubRef.get(0).getTextContent());
	    			}
	    			List<Node> HeadStubRef = getChildrenByTagName(cell.get(l),"HeadStubRef");
	    			if(HeadStubRef!=null&&HeadStubRef.size()>0){
	    				cellItem.setStubHeadRef(HeadStubRef.get(0).getTextContent());
	    			}
	    			List<Node> SuperRowRef = getChildrenByTagName(cell.get(l),"SuperRowRef");
	    			if(SuperRowRef!=null&&SuperRowRef.size()>0){
	    				cellItem.setSuperRowRef(SuperRowRef.get(0).getTextContent());
	    				
	    			}
	    			List<Node> CellRoles = getChildrenByTagName(cell.get(l),"CellRoles");
    				LinkedList<String> cellRolesList = new LinkedList<String>();
	    			for(int o = 0;o<CellRoles.size();o++){
	    				List<Node> CellRole = getChildrenByTagName(CellRoles.get(o),"CellRole");
	    				String CellRoleStr = CellRole.get(0).getTextContent();
	    				cellRolesList.add(CellRoleStr);
	    			}
	    			cellItem.setCellRole(cellRolesList);
	    			tmp_table.cells.add(cellItem);

		    	}
	    	}
	    	
	    }
	    
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return tablesList;
	}
	
	
	/**
	 * Gets the children by tag name.
	 *
	 * @param parent the parent
	 * @param name the name
	 * @return the children by tag name
	 */
	public static List<Node> getChildrenByTagName(Node parent, String name) {
	    List<Node> nodeList = new ArrayList<Node>();
	    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
	      if (child.getNodeType() == Node.ELEMENT_NODE && 
	          name.equals(child.getNodeName())) {
	        nodeList.add((Node) child);
	      }
	    }

	    return nodeList;
	  }
	



}

