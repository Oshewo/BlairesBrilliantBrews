package net.oshewo.blairesbrews.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.oshewo.blairesbrews.block.entity.BrewingKettleBlockEntity;
import net.oshewo.blairesbrews.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BrewingKettleBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public BrewingKettleBlock(Settings settings){
        super(settings);
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.createCuboidShape(9, 1, 6, 10, 2, 7),
            Block.createCuboidShape(1, 0, 1, 15, 1, 15),
            Block.createCuboidShape(2, 1, 1, 14, 2, 2),
            Block.createCuboidShape(1, 1, 1, 2, 4, 15),
            Block.createCuboidShape(2, 1, 14, 14, 4, 15),
            Block.createCuboidShape(14, 1, 1, 15, 4, 15),
            Block.createCuboidShape(2, 1, 8, 14, 2, 14),
            Block.createCuboidShape(5, 1, 7, 14, 2, 8),
            Block.createCuboidShape(11, 1, 6, 14, 2, 7),
            Block.createCuboidShape(6, 1, 6, 7, 2, 7),
            Block.createCuboidShape(13, 1, 5, 14, 2, 6),
            Block.createCuboidShape(8, 1, 4, 9, 2, 5),
            Block.createCuboidShape(11, 1, 4, 12, 2, 5),
            Block.createCuboidShape(3, 1, 7, 4, 2, 8),
            Block.createCuboidShape(4, 1, 5, 5, 2, 6),
            Block.createCuboidShape(5, 1, 3, 6, 2, 4),
            Block.createCuboidShape(6, 3, 8, 10, 4, 12),
            Block.createCuboidShape(6, 4, 7, 10, 8, 8),
            Block.createCuboidShape(5, 8, 9, 6, 10, 12),
            Block.createCuboidShape(0, 8, 10, 16, 9, 11),
            Block.createCuboidShape(14, 4, 8, 15, 8, 13),
            Block.createCuboidShape(1, 4, 8, 2, 8, 13),
            Block.createCuboidShape(1, 8, 8, 2, 9, 9),
            Block.createCuboidShape(10, 4, 8, 11, 8, 12),
            Block.createCuboidShape(6, 4, 12, 10, 8, 13),
            Block.createCuboidShape(5, 4, 8, 6, 8, 12),
            Block.createCuboidShape(10, 8, 9, 11, 10, 12),
            Block.createCuboidShape(1, 8, 12, 2, 9, 13),
            Block.createCuboidShape(14, 8, 8, 15, 9, 9),
            Block.createCuboidShape(14, 8, 12, 15, 9, 13),
            Block.createCuboidShape(10, 2, 10, 14, 3, 14),
            Block.createCuboidShape(2, 2, 13, 3, 3, 14),
            Block.createCuboidShape(13, 2, 9, 14, 3, 10),
            Block.createCuboidShape(13, 3, 11, 14, 4, 12),
            Block.createCuboidShape(11, 3, 13, 12, 4, 14),
            Block.createCuboidShape(13, 3, 13, 14, 4, 14),
            Block.createCuboidShape(8, 2, 9, 9, 3, 10),
            Block.createCuboidShape(3, 2, 11, 4, 3, 12)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.createCuboidShape(9, 1, 9, 10, 2, 10),
            Block.createCuboidShape(1, 0, 1, 15, 1, 15),
            Block.createCuboidShape(14, 1, 2, 15, 2, 14),
            Block.createCuboidShape(1, 1, 1, 15, 4, 2),
            Block.createCuboidShape(1, 1, 2, 2, 4, 14),
            Block.createCuboidShape(1, 1, 14, 15, 4, 15),
            Block.createCuboidShape(2, 1, 2, 8, 2, 14),
            Block.createCuboidShape(8, 1, 5, 9, 2, 14),
            Block.createCuboidShape(9, 1, 11, 10, 2, 14),
            Block.createCuboidShape(9, 1, 6, 10, 2, 7),
            Block.createCuboidShape(10, 1, 13, 11, 2, 14),
            Block.createCuboidShape(11, 1, 8, 12, 2, 9),
            Block.createCuboidShape(11, 1, 11, 12, 2, 12),
            Block.createCuboidShape(8, 1, 3, 9, 2, 4),
            Block.createCuboidShape(10, 1, 4, 11, 2, 5),
            Block.createCuboidShape(12, 1, 5, 13, 2, 6),
            Block.createCuboidShape(4, 3, 6, 8, 4, 10),
            Block.createCuboidShape(8, 4, 6, 9, 8, 10),
            Block.createCuboidShape(4, 8, 5, 7, 10, 6),
            Block.createCuboidShape(5, 8, 0, 6, 9, 16),
            Block.createCuboidShape(3, 4, 14, 8, 8, 15),
            Block.createCuboidShape(3, 4, 1, 8, 8, 2),
            Block.createCuboidShape(7, 8, 1, 8, 9, 2),
            Block.createCuboidShape(4, 4, 10, 8, 8, 11),
            Block.createCuboidShape(3, 4, 6, 4, 8, 10),
            Block.createCuboidShape(4, 4, 5, 8, 8, 6),
            Block.createCuboidShape(4, 8, 10, 7, 10, 11),
            Block.createCuboidShape(3, 8, 1, 4, 9, 2),
            Block.createCuboidShape(7, 8, 14, 8, 9, 15),
            Block.createCuboidShape(3, 8, 14, 4, 9, 15),
            Block.createCuboidShape(2, 2, 10, 6, 3, 14),
            Block.createCuboidShape(2, 2, 2, 3, 3, 3),
            Block.createCuboidShape(6, 2, 13, 7, 3, 14),
            Block.createCuboidShape(4, 3, 13, 5, 4, 14),
            Block.createCuboidShape(2, 3, 11, 3, 4, 12),
            Block.createCuboidShape(2, 3, 13, 3, 4, 14),
            Block.createCuboidShape(6, 2, 8, 7, 3, 9),
            Block.createCuboidShape(4, 2, 3, 5, 3, 4)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.createCuboidShape(6, 1, 9, 7, 2, 10),
            Block.createCuboidShape(1, 0, 1, 15, 1, 15),
            Block.createCuboidShape(2, 1, 14, 14, 2, 15),
            Block.createCuboidShape(14, 1, 1, 15, 4, 15),
            Block.createCuboidShape(2, 1, 1, 14, 4, 2),
            Block.createCuboidShape(1, 1, 1, 2, 4, 15),
            Block.createCuboidShape(2, 1, 2, 14, 2, 8),
            Block.createCuboidShape(2, 1, 8, 11, 2, 9),
            Block.createCuboidShape(2, 1, 9, 5, 2, 10),
            Block.createCuboidShape(9, 1, 9, 10, 2, 10),
            Block.createCuboidShape(2, 1, 10, 3, 2, 11),
            Block.createCuboidShape(7, 1, 11, 8, 2, 12),
            Block.createCuboidShape(4, 1, 11, 5, 2, 12),
            Block.createCuboidShape(12, 1, 8, 13, 2, 9),
            Block.createCuboidShape(11, 1, 10, 12, 2, 11),
            Block.createCuboidShape(10, 1, 12, 11, 2, 13),
            Block.createCuboidShape(6, 3, 4, 10, 4, 8),
            Block.createCuboidShape(6, 4, 8, 10, 8, 9),
            Block.createCuboidShape(10, 8, 4, 11, 10, 7),
            Block.createCuboidShape(0, 8, 5, 16, 9, 6),
            Block.createCuboidShape(1, 4, 3, 2, 8, 8),
            Block.createCuboidShape(14, 4, 3, 15, 8, 8),
            Block.createCuboidShape(14, 8, 7, 15, 9, 8),
            Block.createCuboidShape(5, 4, 4, 6, 8, 8),
            Block.createCuboidShape(6, 4, 3, 10, 8, 4),
            Block.createCuboidShape(10, 4, 4, 11, 8, 8),
            Block.createCuboidShape(5, 8, 4, 6, 10, 7),
            Block.createCuboidShape(14, 8, 3, 15, 9, 4),
            Block.createCuboidShape(1, 8, 7, 2, 9, 8),
            Block.createCuboidShape(1, 8, 3, 2, 9, 4),
            Block.createCuboidShape(2, 2, 2, 6, 3, 6),
            Block.createCuboidShape(13, 2, 2, 14, 3, 3),
            Block.createCuboidShape(2, 2, 6, 3, 3, 7),
            Block.createCuboidShape(2, 3, 4, 3, 4, 5),
            Block.createCuboidShape(4, 3, 2, 5, 4, 3),
            Block.createCuboidShape(2, 3, 2, 3, 4, 3),
            Block.createCuboidShape(7, 2, 6, 8, 3, 7),
            Block.createCuboidShape(12, 2, 4, 13, 3, 5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.createCuboidShape(6, 1, 6, 7, 2, 7),
            Block.createCuboidShape(1, 0, 1, 15, 1, 15),
            Block.createCuboidShape(1, 1, 2, 2, 2, 14),
            Block.createCuboidShape(1, 1, 14, 15, 4, 15),
            Block.createCuboidShape(14, 1, 2, 15, 4, 14),
            Block.createCuboidShape(1, 1, 1, 15, 4, 2),
            Block.createCuboidShape(8, 1, 2, 14, 2, 14),
            Block.createCuboidShape(7, 1, 2, 8, 2, 11),
            Block.createCuboidShape(6, 1, 2, 7, 2, 5),
            Block.createCuboidShape(6, 1, 9, 7, 2, 10),
            Block.createCuboidShape(5, 1, 2, 6, 2, 3),
            Block.createCuboidShape(4, 1, 7, 5, 2, 8),
            Block.createCuboidShape(4, 1, 4, 5, 2, 5),
            Block.createCuboidShape(7, 1, 12, 8, 2, 13),
            Block.createCuboidShape(5, 1, 11, 6, 2, 12),
            Block.createCuboidShape(3, 1, 10, 4, 2, 11),
            Block.createCuboidShape(8, 3, 6, 12, 4, 10),
            Block.createCuboidShape(7, 4, 6, 8, 8, 10),
            Block.createCuboidShape(9, 8, 10, 12, 10, 11),
            Block.createCuboidShape(10, 8, 0, 11, 9, 16),
            Block.createCuboidShape(8, 4, 1, 13, 8, 2),
            Block.createCuboidShape(8, 4, 14, 13, 8, 15),
            Block.createCuboidShape(8, 8, 14, 9, 9, 15),
            Block.createCuboidShape(8, 4, 5, 12, 8, 6),
            Block.createCuboidShape(12, 4, 6, 13, 8, 10),
            Block.createCuboidShape(8, 4, 10, 12, 8, 11),
            Block.createCuboidShape(9, 8, 5, 12, 10, 6),
            Block.createCuboidShape(12, 8, 14, 13, 9, 15),
            Block.createCuboidShape(8, 8, 1, 9, 9, 2),
            Block.createCuboidShape(12, 8, 1, 13, 9, 2),
            Block.createCuboidShape(10, 2, 2, 14, 3, 6),
            Block.createCuboidShape(13, 2, 13, 14, 3, 14),
            Block.createCuboidShape(9, 2, 2, 10, 3, 3),
            Block.createCuboidShape(11, 3, 2, 12, 4, 3),
            Block.createCuboidShape(13, 3, 4, 14, 4, 5),
            Block.createCuboidShape(13, 3, 2, 14, 4, 3),
            Block.createCuboidShape(9, 2, 7, 10, 3, 8),
            Block.createCuboidShape(11, 2, 12, 12, 3, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            default:
                return SHAPE_N;
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /* BLOCK ENTITY */

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BrewingKettleBlockEntity){
                ItemScatterer.spawn(world,pos,(BrewingKettleBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state,world,pos,newState,moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world,pos);

            if(screenHandlerFactory!=null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BrewingKettleBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.BREWING_KETTLE,BrewingKettleBlockEntity::tick);
    }
}
