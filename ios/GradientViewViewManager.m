#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(GradientViewViewManager, RCTViewManager)

RCT_EXPORT_VIEW_PROPERTY(colors, CGColorArray)
RCT_EXPORT_VIEW_PROPERTY(locations, NSArray)
RCT_EXPORT_VIEW_PROPERTY(start, CGPoint)
RCT_EXPORT_VIEW_PROPERTY(end, CGPoint)
RCT_EXPORT_VIEW_PROPERTY(colorList, NSArray)
RCT_EXPORT_VIEW_PROPERTY(angle, CGFloat)

@end
