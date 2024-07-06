package main

import (
	"bufio"
	"fmt"
	"os"
	"os/signal"
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

	// Goroutine to read user input and push to Redis list
	go func() {
		scanner := bufio.NewScanner(os.Stdin)
		for running {
			fmt.Print("Enter Item to enqueue: ")
			scanner.Scan()
			str := scanner.Text()
			err := client.LPush(ctx, "Listt", str).Err()
			if err != nil {
				fmt.Println("Error pushing to Redis:", err)
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
