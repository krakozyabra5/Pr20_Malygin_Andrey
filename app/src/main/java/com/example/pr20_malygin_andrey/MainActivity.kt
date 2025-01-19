package com.example.pr20_malygin_andrey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pr20_malygin_andrey.ui.theme.Pr20_Malygin_AndreyTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pr20_Malygin_AndreyTheme{
                MyApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyApp() {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("Users")

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column (verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        TextField(singleLine = true, modifier = Modifier.padding(start = 35.dp, end = 35.dp, bottom = 40.dp).fillMaxWidth(), shape = RoundedCornerShape(0.dp), colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(0xff253334), focusedContainerColor = Color(0xff253334), unfocusedTextColor = Color.White, focusedTextColor = Color.White), value = name, onValueChange = { name = it }, label = { Text("Имя", color = Color.Gray) })
        TextField(singleLine = true, modifier = Modifier.padding(start = 35.dp, end = 35.dp, bottom = 40.dp).fillMaxWidth(), shape = RoundedCornerShape(0.dp), colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(0xff253334), focusedContainerColor = Color(0xff253334), unfocusedTextColor = Color.White, focusedTextColor = Color.White), value = surname, onValueChange = { surname = it }, label = { Text("Фамилия", color = Color.Gray) })
        TextField(singleLine = true, modifier = Modifier.padding(start = 35.dp, end = 35.dp, bottom = 80.dp).fillMaxWidth(), shape = RoundedCornerShape(0.dp), colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(0xff253334), focusedContainerColor = Color(0xff253334), unfocusedTextColor = Color.White, focusedTextColor = Color.White), value = email, onValueChange = { email = it }, label = { Text("Email", color = Color.Gray) })
        Button (onClick = { if (name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty()) {
            ref.child(email).child("Name").setValue(name)
            ref.child(email).child("Surname").setValue(surname)
        }
        }, modifier = Modifier.padding(bottom = 50.dp).width(150.dp).height(53.dp).align(Alignment.CenterHorizontally), shape = RoundedCornerShape(0.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xff7C9A92))) {
            Text("Save", style = MaterialTheme.typography.titleLarge, letterSpacing = 1.sp)
        }
        Button(onClick = {
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    name = snapshot.child(email).child("Name").getValue(String::class.java).toString()
                    surname = snapshot.child(email).child("Surname").getValue(String::class.java).toString()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }, modifier = Modifier.width(150.dp).height(53.dp).align(Alignment.CenterHorizontally), shape = RoundedCornerShape(0.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xff7C9A92))) {
            Text("View", style = MaterialTheme.typography.titleLarge, letterSpacing = 1.sp)
        }
    }
}