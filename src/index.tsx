import {
  requireNativeComponent,
  UIManager,
  Platform,
  type ColorValue,
  type ViewProps,
  type ProcessedColorValue,
} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-gradient-view' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

type Point = { x: number; y: number };

type AngleDirectionProps = {
  start: Point;
  end: Point;
  angle?: never;
};

type AngleDegreeProps = {
  angle?: number;
  start?: never;
  end?: never;
};

type AngleProps = AngleDirectionProps | AngleDegreeProps;

type LinearLikeProps = {
  colors: ColorValue[];
  locations?: number[];
  colorList?: never;
};

type GradientsLikeProps = {
  colorList: {
    offset: string;
    color: ProcessedColorValue | null | undefined;
  }[];
  colors?: never;
  locations?: never;
};

type GradientViewProps = ViewProps &
  (LinearLikeProps | GradientsLikeProps) &
  AngleProps;

const ComponentName = 'GradientViewView';

export const GradientViewView =
  UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<GradientViewProps>(ComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
      };
