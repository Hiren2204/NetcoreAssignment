package main

import (
	"bufio"
	"fmt"
	"os"
	"os/signal"
	"strconv"
	"syscall"

	"github.com/go-redis/redis/v8"
	"golang.org/x/net/context"
)

var running = true

func main() {
	ctx := context.Background()
	client := redis.NewClient(&redis.Options{
		Addr: "localhost:6379",
	})

	// Goroutine to display and optionally update the Redis list
	go func() {
		scanner := bufio.NewScanner(os.Stdin)
		for running {
			// Retrieve and display the list
			li, err := client.LRange(ctx, "Listt", 0, -1).Result()
			if err != nil {
				fmt.Println("Error fetching from Redis:", err)
			}
			fmt.Println("Items in List are:", li)

			// Prompt to update the list
			fmt.Println("To Update List Enter 1")
			scanner.Scan()
			input := scanner.Text()
			i, err := strconv.Atoi(input)
			if err != nil {
				fmt.Println("Invalid input, please enter a number.")
				continue
			}
			if i == 1 {
				running = true
			} else {
				running = false
			}
		}
	}()

	// Handle graceful shutdown
	sigs := make(chan os.Signal, 1)
	signal.Notify(sigs, syscall.SIGINT, syscall.SIGTERM)

	<-sigs
	running = false
	fmt.Println("Shutting down...")
}
