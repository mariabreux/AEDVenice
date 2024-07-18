package com.finalproject.aedvenice.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink

@Composable
fun ManageUsersScreen(viewModel: ViewModel, navController: NavHostController){
    //val users = viewModel.getUsers()



    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Users",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .size(60.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    navController.navigate("Registration")
                }
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

            }
        }


        Spacer(modifier = Modifier.padding(30.dp))

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Email", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Actions", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.padding(15.dp))

//            Column(
//                modifier = Modifier
//                    .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(5.dp))
//            ) {
//                users.forEach { user ->
//                    Row {
//                        Text(
//                            text = user.email ?: "", modifier = Modifier
//                                .weight(1f)
//                                .padding(horizontal = 5.dp)
//                                .align(Alignment.CenterVertically)
//                        )
//
//                        Divider(
//                            modifier = Modifier
//                                .height(50.dp)
//                                .width(1.dp),
//                            color = Color.LightGray
//                        )
//
//
//
//                            IconButton(onClick = {
//                                user.id?.let {
//                                    viewModel.deleteUser(it, {
//                                        Log.d("Delete User", "User deleted")
//                                    },
//                                        {
//                                            Log.e("Delete User", "Error deleting user")
//
//                                        })
//                                }
//                            }) {
//                                Icon(
//                                    imageVector = ImageVector.vectorResource(id = R.drawable.delete),
//                                    tint = Color.Unspecified,
//                                    contentDescription = "delete",
//                                    modifier = Modifier
//                                        .size(25.dp)
//                                )
//                            }
//                        Divider()
//                    }
//                }
//
//            }


        }


    }
}