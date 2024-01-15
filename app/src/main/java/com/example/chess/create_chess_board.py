f = open("chess_board.txt", "w")
k = True
for i in range(7,-1,-1):
    k = not(k)
    for j in range(8):
        if k == False :
            f.write(f"""  <ImageButton
            android:id="@+id/button{i}{j}"
            android:layout_width="45dp"
            android:layout_height="45dp"
            android:layout_weight="1"
            android:background="@drawable/green_tile" />\n""")

        else :
            f.write(f"""  <ImageButton
            android:id="@+id/button{i}{j}"
            android:layout_width="45dp"
            android:layout_height="45dp"
            android:layout_weight="1"
            android:background="@drawable/white_tile" />\n""")

        k= not(k)
f.close()
