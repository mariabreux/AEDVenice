package com.finalproject.aedvenice.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.ui.theme.DarkPink
import com.finalproject.aedvenice.ui.theme.LightPink
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun HowToActScreen(context: Context, navController: NavHostController){
    val instructions = readInstructions(context)
    val sections = parseInstructions(instructions)
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(25.dp)
    ) {
        sections.forEach { section ->
            item {
                ExpandableSection(title = section.title, items = section.items)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
@Composable
fun ExpandableSection(title: String, items: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = DarkPink
            )
        }
        if (expanded) {
            items.forEach { item ->
                Text(
                    text = "• $item",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 32.dp, top = 2.dp)

                )
            }
        }
    }
}

private fun parseInstructions(instructions: String): List<InstructionSection>{
    val sections = mutableListOf<InstructionSection>()
    val lines = instructions.split("\n")
    var currentSection: InstructionSection?= null

    for(line in lines){
        if(line.isNotBlank()){
            if(!line.startsWith("•")){
                if(currentSection != null)
                    sections.add(currentSection)

                currentSection = InstructionSection(title = line, items = mutableListOf())
            } else{
                currentSection?.items?.add(line.removePrefix("•"))
            }
        }
    }
    currentSection?.let { sections.add(it) }
    return sections
}
data class InstructionSection(val title: String, val items: MutableList<String>)
private fun readInstructions(context: Context): String {
    val inputStream = context.resources.openRawResource(R.raw.instructions)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val stringBuilder = StringBuilder()
    var line: String? = reader.readLine()
    while(line != null){
        stringBuilder.append(line).append("\n")
        line = reader.readLine()
    }
    reader.close()
    return stringBuilder.toString()
}


@Preview(showBackground = true)
@Composable
fun HowToActScreenPreview(){
    //HowToActScreen(rememberNavController())
}
