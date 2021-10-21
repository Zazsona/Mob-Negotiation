package com.zazsona.mobnegotiation.view;

import com.zazsona.mobnegotiation.view.interfaces.IClickableNegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.IContainerNegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.INegotiationView;
import com.zazsona.mobnegotiation.view.interfaces.IViewInteractionExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class NegotiationViewInteractionExecutor implements CommandExecutor, IViewInteractionExecutor
{
    public static final String COMMAND_KEY = "__negotiation_response__";
    private static NegotiationViewInteractionExecutor instance;

    private HashMap<String, INegotiationView> viewMap;

    public static NegotiationViewInteractionExecutor getInstance()
    {
        if (instance == null)
            instance = new NegotiationViewInteractionExecutor();
        return instance;
    }

    private NegotiationViewInteractionExecutor()
    {
        this.viewMap = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player && args.length > 0)
        {
            INegotiationView rootView = viewMap.get(args[0]);
            if (rootView != null)
                traverseViewTree(rootView, args, 0);
        }
        return true;
    }

    /**
     * Traverses the view tree, activating the final leaf views where possible
     * @param view the view to traverse
     * @param ids the ids to navigate through
     * @param level the current level of the tree
     */
    private void traverseViewTree(INegotiationView view, String[] ids, int level)
    {
        if (view instanceof IContainerNegotiationView && ids.length > (level + 1))
        {
            IContainerNegotiationView containerView = (IContainerNegotiationView) view;
            INegotiationView child = containerView.getChild(ids[level + 1]);
            traverseViewTree(child, ids, level + 1);
        }
        else if (view instanceof IClickableNegotiationView) // Leaf node
        {
            IClickableNegotiationView clickableView = (IClickableNegotiationView) view;
            clickableView.click();
        }
    }

    /**
     * Adds a view and its children to receives interactions
     * @param view the view to add
     */
    @Override
    public void addView(INegotiationView view)
    {
        viewMap.put(view.getId(), view);
    }

    /**
     * Removes a view and its children from receiving interactions
     * @param view the view to remove
     */
    @Override
    public INegotiationView removeView(INegotiationView view)
    {
        return removeView(view.getId());
    }

    /**
     * Removes a view and its children from receiving interactions
     * @param id the id of the view to remove
     */
    @Override
    public INegotiationView removeView(String id)
    {
        return viewMap.remove(id);
    }
}
