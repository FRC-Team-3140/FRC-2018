@echo off
echo Making git assume .classpath is unchanged...
git update-index --skip-worktree .classpath
echo Done.
pause
