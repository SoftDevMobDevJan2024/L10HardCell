# Background tasks

This app shows a few different approaches to doing tasks in the background and 
off the main thread.

The app draws a cellular automata, which is a grid containing cells that are on or off,
and that are generated following a particular pattern. They are 
handy for this task as they take some time to generate. You note you have a switch
to change between a small and large grid.

This app has been improved from the original version to have 3 separate helper classes, one to demonstrate each type of drawing:
1. DrawBlockingOnUI: drawing on main thread
2. DrawThreadedLooper: drawing using Handler and LooperThread
3. DrawThreadedCoroutine: drawing using coroutine

Firstly, try running the code on the main thread. When in large mode, try clicking
on other buttons or the input field. This should trigger an ANR dialog eventually.

The other approaches don't look like they are doing much different. The thing
to look at is the code though -- the coroutines code is much shorter and 
cleaner than what is required for the looper approach (a whole new class!).