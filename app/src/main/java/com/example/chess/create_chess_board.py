
l = ['A','B','C', 'D', 'E', 'F', 'G', 'H']
f = open("chess_board.txt", "w")
k = True
for i in range(1,9):
    k = not(k)
    for letter in l:
        if k == False :
            f.write(f"""  <ImageButton 
            android:id="@+id/button{letter}{i}"
            android:layout_width="45dp"
            android:layout_height="45dp"
            android:layout_weight="1"
            android:background="@drawable/green_tile" />\n""")

        else :
            f.write(f"""  <ImageButton 
            android:id="@+id/button{letter}{i}"
            android:layout_width="45dp"
            android:layout_height="45dp"
            android:layout_weight="1"
            android:background="@drawable/white_tile" />\n""")

        k= not(k)
f.close()
