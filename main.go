package main

import (
	"context"
	"fmt"
	"os"
	"time"

	"github.com/redis/go-redis/v9"
	_ "github.com/redis/go-redis/v9"
)

func main() {
	var ctx = context.Background()
	file, err := os.Create("Output.txt")
	fmt.Print("")
	red := redis.NewClient(&redis.Options{
		Addr:     "localhost:6379",
		Password: "",
		DB:       0,
	})
	if err := red.Ping(ctx).Err(); err != nil {
		fmt.Printf("Error pinging Redis: %v\n", err)
		return
	} else {
		fmt.Println("Successfully connected to Redis")
	}
	err = red.Set(ctx, "Mey", "Abc", 0).Err()

	if err != nil {
		fmt.Printf("val %v", err)
	}

	val, err := red.Get(ctx, "MyKey").Result()

	if err != nil {
		fmt.Print(err)

	} else {
		fmt.Printf("%s val", val)
	}

	//Rpush
	// a := []string{"anv", "Adsa"}
	// for _, val := range a {
	// 	err = red.RPush(ctx, "tasks", val).Err()
	// 	if err != nil {
	// 		fmt.Print(err)
	// 	}
	// }
	// // an := red.LRange(ctx, "tasks", 0, -1)
	// // fmt.Print(an)

	// //lpush
	// b := []string{"l11", "l22"}
	// for _, val := range b {
	// 	err = red.LPush(ctx, "tasks", val).Err()
	// 	if err != nil {
	// 		fmt.Print(err)
	// 	}
	// }
	// an := red.LRange(ctx, "tasks", 0, -1)
	// fmt.Print(an)

	// // Lpop
	// 	lp := red.LPop(ctx, "tasks")

	// 	//rpop
	// 	rp := red.RPop(ctx, "tasks")

	// 	_, err = file.WriteString(fmt.Sprint("Lpoped Data:", lp, " right poped data : ", rp))
	// 	if err != nil {
	// 		fmt.Println(err)
	// 	}

	// 	// fmt.Print("\nlpop: ", lp, " rpop: ", rp)

	// 	// err = red.HSet(ctx, "User1", map[string]interface{}{
	// 	// 	"Name": "Abc",
	// 	// 	"ID":   "11",
	// 	// }).Err()
	// 	// if err != nil {
	// 	// 	fmt.Print(err)
	// 	// }

	// 	data, err := red.HGetAll(ctx, "User1").Result()

	// 	// fmt.Println(data)

	// 	if err != nil {
	// 		fmt.Println(err)
	// 	}

	// 	_, err = file.WriteString(fmt.Sprintln("\n", data))
	// 	if err != nil {
	// 		fmt.Println(err)
	// 	}
	ticker := time.NewTicker(10 * time.Second)

	for t := range ticker.C {
		fmt.Println(t)
		writingFile(red, ctx, file, t)
	}
}

func writingFile(red *redis.Client, ctx context.Context, file *os.File, t time.Time) {
	lp := red.LPop(ctx, "tasks")

	//rpop
	rp := red.RPop(ctx, "tasks")

	_, err := file.WriteString(fmt.Sprint("", t, "Lpoped Data:", lp, " right poped data : ", rp))
	if err != nil {
		fmt.Println(err)
	}
	data, err := red.HGetAll(ctx, "User1").Result()
	if err != nil {
		fmt.Println(err)
	}

	_, err = file.WriteString(fmt.Sprintln("\n", data))
	if err != nil {
		fmt.Println(err)
	}

}
