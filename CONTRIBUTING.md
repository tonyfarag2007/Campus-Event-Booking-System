# How to contribute without blowing up this project
###### Originally covered by Verbum in the project Discord, see "How to use Git and PRs under the #resources channel"
Welcome everyone here is a step by step tutorial to using git and PRs with the GUI of IntelliJ and github
Before starting to work on something
## Step 1
Either in IntelliJ or through the command line, pull down changes from the `master` branch:
- **ONLY IF YOU ARE ON `master` ALREADY**. navigate to the Git branch icon in the top-left corner and click the "Downward Arrow" button. It will show "Fetch" on hover. 
  - If you are not already on `master`, go down to the `Remote` section, hover over the `master` branch, then select `Checkout`.
- `git fetch` provided you are on `master`, check with `git branch`, you can switch to `master` with `git switch master`
## Step 2
Create a new branch:
- In IntelliJ, go to the Git branch icon, select `New Branch`, then enter a nice name for your branch.
- On the command line, you can run `git switch -c <branch name>` to create the new branch then switch to it.

As you work on your project in IntelliJ, you will be prompted to add new files to git, **make sure you do this unless you wish to add them later!**

On the command line, you can add files with `git add <files and/or directories>`. Make sure to only add the stuff that YOU made and wish to later marge!
## Step 3
Once you're fine with your changes, you can commit them.
- In IntellIj, go to the Git branch icon, select `Commit`, and add the changes you want. Ignore the `Unversioned Files` unless you forgot to add one/some of them, which you'd previously made. Write a descriptive commit message to summarize what you've done thus far. When you're done, select `Commit and Push`.
- On the command line, just run `git commit -m <commit message>` provided you are on the branch you made previously. Finally, run `git push origin <branch you made>`.
## Step 4
Navigate to the GitHub repo for this project and go to the `Pull Requests` tab. Create a new pull request and select the branch you pushed previously in Step 3 to compare to. 

Click `Create Pull Request` once you're done and you should be prompted to create a Merge Request. Add a descriptive message of your changes/reasons for the merge. If all is well, you should be able to merge to `master`, **unless stated otherwise (i.e. we are resolving a merge conflict or some other nightmare.)**
## "There's a merge conflict, what do I do?????"
A merge conflict means that your repository does not have some commits which have been made to `master`.

If there are merge conflicts, it won't let you merge the pull request you made. In this case, we must resolve conflicting lines in our merge to pick what changes to keep/discard in the merge. IntellIj can help with this.

To enter this window, you have to first:
- Pull down `master` (first step of the tutorial)
- Click on the `master` branch and then hit `Merge master into <your-branch>` (See image #2)
  - Upon doing that, if there are merge conflicts the resolution window shows up. Depending on what you're doing / has been done, you either choose your implementation OR `master`'s implementation.
  **MAKE SURE TO TEST NOW THAT YOU'VE MERGED MASTER.**
- If all is good, go back to GitHub and attempt to merge again.