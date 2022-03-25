package dk.itu.gamecreator.android.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Components.ImageComponent;
import dk.itu.gamecreator.android.Components.MultipleChoiceComponent;
import dk.itu.gamecreator.android.Components.TextComponent;
import dk.itu.gamecreator.android.Components.TextSolutionComponent;
import dk.itu.gamecreator.android.Fragments.CreateComponentFragment;
import dk.itu.gamecreator.android.Fragments.CreateImageFragment;
import dk.itu.gamecreator.android.Fragments.CreateMultipleChoiceFragment;
import dk.itu.gamecreator.android.Fragments.CreateTextFragment;
import dk.itu.gamecreator.android.Fragments.CreateTextSolutionFragment;
import dk.itu.gamecreator.android.R;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemMoveCallback.ItemTouchHelperContract {

    private final List<Component> components;
    private final LayoutInflater mInflater;
    private final Context context;

    private static final int TYPE_TEXT = 1;
    private static final int TYPE_SOLUTION_TEXT = 2;
    private static final int TYPE_IMAGE = 3;
    private static final int TYPE_MULTI = 4;

    public RecyclerViewAdapter(Context context, List<Component> components) {
        this.mInflater = LayoutInflater.from(context);
        this.components = components;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT) { // TextComponent
            View view = mInflater.inflate(R.layout.recycler_text, parent, false);
            return new TextViewHolder(view);
        } else if (viewType == TYPE_SOLUTION_TEXT) {
            View view = mInflater.inflate(R.layout.recycler_text_solution, parent, false);
            return new TextSolutionViewHolder(view);
        } else if (viewType == TYPE_IMAGE) {
            View view = mInflater.inflate(R.layout.recycler_image, parent, false);
            return new ImageViewHolder(view);
        } else if (viewType == TYPE_MULTI) {
            View view = mInflater.inflate(R.layout.recycler_multi, parent, false);
            return new MultiViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Component component = components.get(position);

        if (getItemViewType(position) == TYPE_TEXT) { // TextComponent
            TextViewHolder textHolder = (TextViewHolder) holder;

            String text = ((TextComponent) component).getText();
            textHolder.componentText.setText(text);

            textHolder.deleteButton.setOnClickListener(view -> onDelete(textHolder));
            textHolder.editButton.setOnClickListener(view -> onEdit(textHolder));

        } else if (getItemViewType(position) == TYPE_SOLUTION_TEXT) {
            TextSolutionViewHolder textSolutionHolder = (TextSolutionViewHolder) holder;

            String text = ((TextSolutionComponent) component).getSolutionText();
            textSolutionHolder.solutionText.setText(text);

            String buttonText = ((TextSolutionComponent) component).getButtonText();
            textSolutionHolder.buttonText.setText(buttonText);

            textSolutionHolder.deleteButton.setOnClickListener(view -> onDelete(textSolutionHolder));
            textSolutionHolder.editButton.setOnClickListener(view -> onEdit(textSolutionHolder));

        } else if (getItemViewType(position) == TYPE_IMAGE) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            Bitmap bitmap;
            if (component instanceof ImageComponent) {
                bitmap = ((ImageComponent) component).getBitmap();
                imageViewHolder.imageView.setImageBitmap(bitmap);
            }
            imageViewHolder.deleteButton.setOnClickListener(view -> onDelete(imageViewHolder));
            imageViewHolder.editButton.setOnClickListener(view -> onEdit(imageViewHolder));
        } else if (getItemViewType(position) == TYPE_MULTI) {
            MultiViewHolder multiViewHolder = (MultiViewHolder) holder;
            multiViewHolder.deleteButton.setOnClickListener(view -> onDelete(multiViewHolder));
            multiViewHolder.editButton.setOnClickListener(view -> onEdit(multiViewHolder));
        }
    }

    private void onDelete(RecyclerView.ViewHolder holder) {
        components.remove(holder.getAdapterPosition());
        notifyItemRemoved(holder.getAdapterPosition());
        notifyItemRangeChanged(holder.getAdapterPosition(), components.size());
    }

    private void onEdit(RecyclerView.ViewHolder holder) {
        Bundle bundle = new Bundle();
        bundle.putInt("componentIndex", holder.getAdapterPosition());

        CreateActivity activity = (CreateActivity) context;
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.create_fragment, CreateComponentFragment.class, bundle)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public int getItemViewType(int position) {
        if (components.get(position) instanceof TextComponent) {
            return TYPE_TEXT;
        } else if (components.get(position) instanceof TextSolutionComponent) {
            return TYPE_SOLUTION_TEXT;
        } else if (components.get(position) instanceof ImageComponent) {
            return TYPE_IMAGE;
        } else if (components.get(position) instanceof MultipleChoiceComponent) {
            return TYPE_MULTI;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(components, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(components, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(ViewHolder viewHolder) {
        viewHolder.rowView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(ViewHolder viewHolder) {
        viewHolder.rowView.setBackgroundColor(Color.WHITE);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View rowView;

        ViewHolder(View itemView) {
            super(itemView);
            rowView = itemView;
        }
    }

    public static class MultiViewHolder extends ViewHolder {
        TextView componentText;
        Button editButton;
        Button deleteButton;

        MultiViewHolder(View itemView) {
            super(itemView);
            componentText = itemView.findViewById(R.id.component_text);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

    // TextComponent
    public static class TextViewHolder extends ViewHolder {
        TextView componentText;
        Button editButton;
        Button deleteButton;

        TextViewHolder(View itemView) {
            super(itemView);
            componentText = itemView.findViewById(R.id.component_text);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

    // TextSolutionComponent
    public static class TextSolutionViewHolder extends ViewHolder {
        TextView solutionText;
        TextView buttonText;
        Button editButton;
        Button deleteButton;

        TextSolutionViewHolder(View itemView) {
            super(itemView);
            solutionText = itemView.findViewById(R.id.solution_text);
            buttonText = itemView.findViewById(R.id.button_text);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

    public static class ImageViewHolder extends ViewHolder {
        Button editButton;
        Button deleteButton;
        ImageView imageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
