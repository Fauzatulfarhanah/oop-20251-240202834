package com.upb.agripos;

/**
 * Kelas untuk mencetak bagian refrain lagu "Night Changes" oleh One Direction dengan efek typing per huruf sesuai irama.
 */
public class NightChanges {

    public static void main(String[] args) {
        printRefrain();
    }

    /**
     * Metode untuk mencetak bagian refrain lagu "Night Changes" dengan efek typing per huruf untuk mensimulasikan irama musik.
     */
    public static void printRefrain() {
        String[] lines = {
            "We're only getting older baby",
            "And I've been thinking about it lately",
            "Does it ever drive you crazy",
            "Just how fast the night changes",
            "Everything that you've ever dreamed of",
            "Disappearing when you wake up",
            "But there's nothing to be afraid of",
            "Even when the night changes",
            "It will never change me and you"
        };

        try {
            for (String line : lines) {
                for (char c : line.toCharArray()) {
                    System.out.print(c);
                    Thread.sleep(100); // Jeda 100ms per huruf untuk efek irama
                }
                System.out.println(); // Baris baru setelah selesai
                Thread.sleep(500); // Jeda 500ms antara baris
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
