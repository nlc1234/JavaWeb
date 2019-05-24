package hotWords.util;

import java.io.IOException;  
  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.mapreduce.InputSplit;  
import org.apache.hadoop.mapreduce.RecordReader;  
import org.apache.hadoop.mapreduce.TaskAttemptContext;  
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;  
  
public class FileInput extends FileInputFormat<Text,Text>{  
  
    @Override  
    public RecordReader<Text, Text> createRecordReader(InputSplit arg0, TaskAttemptContext arg1) throws IOException,  
            InterruptedException {  
        // TODO Auto-generated method stub  
        RecordReader<Text,Text> recordReader = new FileRecordReader();  
        return recordReader;  
    }  
  
}  
